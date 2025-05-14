package drinkselector.drinks.Event.EventHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Etcs.RedisUtill.ReCentSearchLog;
import drinkselector.drinks.Event.Events.RecentSearchLogEvent;
import drinkselector.drinks.Event.Events.GetRecentSearchLongEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RecentSearchLongEventHandler {


    private final ReCentSearchLog reCentSearchLog;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String,String> redisTemplate;



    @Async
    @EventListener(RecentSearchLogEvent.class)
    public void add_search_log(RecentSearchLogEvent recentSearchLogEvent){

        if(recentSearchLogEvent.getMember_id()==null){
        reCentSearchLog.real_time_search_log(recentSearchLogEvent.getDrink_id(),recentSearchLogEvent.getDrink_name());}
        else{

            reCentSearchLog.update_recent_search_log(recentSearchLogEvent.getMember_id(),recentSearchLogEvent.getDrink_id(),recentSearchLogEvent.getDrink_name());
        }
    }
    @EventListener(GetRecentSearchLongEvent.class)
    public void get_realtime_issue(GetRecentSearchLongEvent recentSearchLongUuidEvent){



        List<RecentSearchShowDto> recentSearchDtos= reCentSearchLog.return_realtime_issue();

        try {
            redisTemplate.opsForValue().set(recentSearchLongUuidEvent.getWork_id(), objectMapper.writeValueAsString(recentSearchDtos),20, TimeUnit.SECONDS);
        }
        catch (Exception e){
            throw new RuntimeException();
        }

    }


}
