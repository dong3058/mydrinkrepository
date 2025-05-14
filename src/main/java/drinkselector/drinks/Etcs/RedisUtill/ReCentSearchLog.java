package drinkselector.drinks.Etcs.RedisUtill;


import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Entity.ThumbNail;
import drinkselector.drinks.Repository.ThumbRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReCentSearchLog {


    private final RedisTemplate<String,String> redisTemplate;

    private final ThumbRepository thumbRepository;



    @PostConstruct
    @Scheduled(cron=" 0 0 * * * *")
    public void update_real_time_key(){
        LocalDateTime now=LocalDateTime.now();
        int year=now.getDayOfYear();
        int month=now.getMonthValue();
        int day=now.getDayOfMonth();
        int hour=now.getHour();


        redisTemplate.opsForZSet().add("%s%s%s%s".formatted(year,month,day,hour),"",0);
        redisTemplate.expire("%s%s%s%s".formatted(year,month,day,hour),3660,TimeUnit.SECONDS);

    }


    public void real_time_search_log(Long search_id,String search_word){

        LocalDateTime now=LocalDateTime.now();
        int year=now.getDayOfYear();
        int month=now.getMonthValue();
        int day=now.getDayOfMonth();
        int hour=now.getHour();

        redisTemplate.opsForZSet().incrementScore("%s%s%s%s".formatted(year,month,day,hour),"%s-%s".formatted(search_id,search_word),1);

    }


    public List<RecentSearchShowDto> return_realtime_issue(){

        LocalDateTime now=LocalDateTime.now();
        int year=now.getDayOfYear();
        int month=now.getMonthValue();
        int day=now.getDayOfMonth();
        int hour=now.getHour();
        Set<String> real_time_issue=redisTemplate.opsForZSet().reverseRange("%s%s%s%s".formatted(year,month,day,hour),0,10);

        return real_time_issue.stream().map(x->{
            String [] arrs=x.split("-");
            Long drink_id=Long.parseLong(arrs[0]);
            Optional<ThumbNail> thumb_path=thumbRepository.find_by_drink_id(drink_id);

            return new RecentSearchShowDto(drink_id,arrs[1],thumb_path.get().getThumb_path());
        }).collect(Collectors.toList());


    }




    public void update_recent_search_log(Long user_id, Long search_id, String search_word){



        String now=LocalDateTime.now().toString();

        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations operations) throws DataAccessException {

                operations.multi();
                ZSetOperations<String,String> op=operations.opsForZSet();
                op.removeRange("search_by_%s".formatted(user_id),-10,-10);
                op.add("search_by_%s".formatted(user_id),"%s-%s".formatted(search_id,search_word), Double.parseDouble(now));

                return operations.exec();


            }
        }
        );




    }

    public List<RecentSearchShowDto> get_recent_search_log(Long user_id){

        Set<String> search_list=redisTemplate.opsForZSet().range("search_by_%s".formatted(user_id),0,9);

        return search_list.stream().map(x->{
           String [] arrs=x.split("-");
           return new RecentSearchShowDto(Long.parseLong(arrs[0]),arrs[1],null);
        }).collect(Collectors.toList());


    }

}
