package drinkselector.drinks.Etcs.RedisUtill;


import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Entity.ThumbNail;
import drinkselector.drinks.Etcs.Enums.RedisKeyEnum;
import drinkselector.drinks.Etcs.Enums.RedisOpEnum;
import drinkselector.drinks.Repository.ThumbRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.NOP_FallbackServiceProvider;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisUtills {


    private final RedisTemplate<String,String> redisTemplate;

    private final ThumbRepository thumbRepository;


    /*
    *
    * 레디스 저장된 내용 리스트
    * 1."년월요일시간"--> zset으로 스코어 보드화, 시간단위로 검색어값을 키로 몇개가 검색되었는지를 기록
    * 2.serach_by_{user_id}:이걸 키값으로 유저가 검색한 검색어를 기록 10개 가최대
    * 3.hash type  member_mail--> 로그인 횟수  email 리스트 로그인 보안 필요여부
    * 4.user_login_count
    * 5.user_login_ip
      6.member_mail-->보안코드 5분ttl
      * 로할건대 hash 타입으로 key=메일 value=
    * */


    @PostConstruct
    @Scheduled(cron=" 0 0 * * * *")
    public void update_real_time_key(){
        LocalDateTime now=LocalDateTime.now();
        int year=now.getDayOfYear();
        int month=now.getMonthValue();
        int day=now.getDayOfMonth();
        int hour=now.getHour();

        redisTemplate.execute(new SessionCallback<Object>() {


            @Override
            public  Object execute(RedisOperations operations) throws DataAccessException {
               operations.multi();
               ZSetOperations<String,String> zop=operations.opsForZSet();
                zop.add("%s%s%s%s".formatted(year,month,day,hour),"",0);
                operations.expire("%s%s%s%s".formatted(year,month,day,hour),3660,TimeUnit.SECONDS);
                zop.remove("%s%s%s%s".formatted(year,month,day,hour),"");
               operations.exec();

                return null;
            }
        });




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
        if(real_time_issue.size()>0){
            log.info("size:{}",real_time_issue.size());
            List<String []> arr_list=real_time_issue.stream().map(x->{
                String [] arrs=x.split("-");
                return arrs;
            }).toList();
            List<Long> ids=arr_list.stream().filter(x->{
                if(x.length>0){
                    return true;
                }
                return false;

            }).map(x->{

                return Long.parseLong(x[0]);
            }).toList();

            List<ThumbNail> thumbNails=thumbRepository.find_by_drink_id(ids);
                return IntStream.range(0,thumbNails.size())
                .mapToObj(x->{

                    ThumbNail t=thumbNails.get(x);
                    String [] arr=arr_list.get(x);

                    return new RecentSearchShowDto(t.getDrink_id(),arr[1],t.getImage_path());
                }).collect(Collectors.toList());
        }
        return List.of();
    }




    public void update_recent_search_log(Long user_id, Long search_id, String search_word){

        LocalDateTime now=LocalDateTime.now();

        Long now_By_num=System.currentTimeMillis();
        int year=now.getDayOfYear();
        int month=now.getMonthValue();
        int day=now.getDayOfMonth();
        int hour=now.getHour();


        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations operations) throws DataAccessException {




                operations.multi();
                ZSetOperations<String,String> op=operations.opsForZSet();
                op.incrementScore("%s%s%s%s".formatted(year,month,day,hour),"%s-%s".formatted(search_id,search_word),1);
                op.removeRange("search_by_%s".formatted(user_id),-10,-10);
                op.add("search_by_%s".formatted(user_id),"%s-%s".formatted(search_id,search_word),(double) now_By_num);

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


    public boolean Check_Security_Login_Require(String ip,String mail){

          /* user_login_count
                * user_login_ip

           */

        List<String> hashkeys=List.of(RedisKeyEnum.User_Login_Ip.getKey(),RedisKeyEnum.User_Login_Count.getKey());
        List<Object> datas=redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection stringRedisConnection=(StringRedisConnection) connection;

                hashkeys.stream().forEach(x->{

                    stringRedisConnection.hGet(x,mail);
                });


                return null;
            }
        });


        List<Integer> list=IntStream.range(0,datas.size())
                .filter(x->{

                    if(x==0){
                        String value=(String) datas.get(x);
                        if(!value.equals(ip)){
                            return true;
                        }
                        return false;
                    } else{
                        String value=String.valueOf(datas.get(1));

                        if(Long.parseLong(value)>=5L){
                            return true;
                        }
                        return false;
                    }

                }).boxed().collect(Collectors.toList());


        log.info("list size:{}", list.size());
        if (list.size() > 0) {

            return true;
        }

        return false;

    }

    public void Init_Security_Login_Check(String email){
        redisTemplate.opsForHash().put("user_login_count",email,"0");

    }



    public  void RedisValueSetOperation(String key,String value,Long time,TimeUnit timeUnit){

        redisTemplate.opsForValue().set(key,value,time,timeUnit);

    }
    public  void RedisValueSetOperation(String key,Long value,Long time,TimeUnit timeUnit){

        redisTemplate.opsForValue().set(key,String.valueOf(value),time,timeUnit);

    }

    public Optional<String> RedisValueGetOperation(String key){
        Optional<String> val=Optional.ofNullable((String) redisTemplate.opsForValue().get(key));
        return val;

    }
    public Optional<String> RedisHashGetOperation(String key, String hash_key){


        Optional<String> val=Optional.ofNullable((String)  redisTemplate.opsForHash().get(key,hash_key));

        return val;

    }

    public void RedisOpsSetStructureSetOperation(String key,String value){

        redisTemplate.opsForSet().add(key,value);
    }

    public Map<Object,Object> RedisOpsHashGetEntryOperation(String key){


       return  redisTemplate.opsForHash().entries(key);


    }

    public Long RedisOpsSetStructureGetSizeOperation(String key){

         return redisTemplate.opsForSet().size(key);
    }

    public void RedisHashSetOperation(String key,String hash_key,String field){
        redisTemplate.opsForHash().put(key,hash_key,field);
    }


    public  Map.Entry<Object,Object>  GetDrinkRandomEntry(){
        return redisTemplate.opsForHash().randomEntry("drink");
    }

    public void Make_Redis_Pipeline(List<RedisOperationDto> redisOperationDtos){
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {


                StringRedisConnection stringRedisConnection=(StringRedisConnection) connection;
                redisOperationDtos.stream().forEach(x->{

                    if(x.redisOpEnum().equals(RedisOpEnum.HashSet)){

                        stringRedisConnection.hSet(x.key(),x.hash_key(),x.string_value());



                    } else if (x.redisOpEnum().equals(RedisOpEnum.ValueSet)) {


                        stringRedisConnection.set(x.key(),x.string_value());

                    } else if (x.redisOpEnum().equals(RedisOpEnum.ValueDelete)) {
                        stringRedisConnection.del(x.key());
                    } else if (x.redisOpEnum().equals(RedisOpEnum.HashDelete)) {

                        stringRedisConnection.hDel(x.key(),x.hash_key());

                    }

                });



                return null;
            }
        });

    }


    public void Make_Redis_Tx(List<RedisOperationDto> redisOperationDtos){


        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                HashOperations<String,String,String> hop=operations.opsForHash();
                ZSetOperations<String,String>zop=operations.opsForZSet();
                SetOperations<String,String>sop=operations.opsForSet();
                ValueOperations<String,String> vop=operations.opsForValue();

                redisOperationDtos.stream().forEach(x->{

                    if(x.redisOpEnum().equals(RedisOpEnum.HashSet)){


                        hop.put(x.key(),x.hash_key(),x.string_value());
                    } else if (x.redisOpEnum().equals(RedisOpEnum.SetAdd)) {

                        sop.add(x.key(),x.string_value());

                    }
                    else if (x.redisOpEnum().equals(RedisOpEnum.HashDelete)){
                        //추후에 개선좀 해야될듯 ㅇㅇ; 생성자에서 문제가생김 빌더로 수정해야되나?
                        hop.delete(x.key(),x.string_value());
                    } else if (x.redisOpEnum().equals(RedisOpEnum.SetDelete)) {

                        sop.remove(x.key(),x.string_value());

                    }


                });


                operations.exec();

                return null;
            }
        });

    }
}
