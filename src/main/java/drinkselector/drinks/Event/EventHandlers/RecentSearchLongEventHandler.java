package drinkselector.drinks.Event.EventHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
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


    private final RedisUtills redisUtills;


    @Async
    @EventListener(RecentSearchLogEvent.class)
    public void add_search_log(RecentSearchLogEvent recentSearchLogEvent){

        if(recentSearchLogEvent.member_id()==null){
        redisUtills.real_time_search_log(recentSearchLogEvent.drink_id(),recentSearchLogEvent.drink_name());}
        else{

            redisUtills.update_recent_search_log(recentSearchLogEvent.member_id(),recentSearchLogEvent.drink_id(),recentSearchLogEvent.drink_name());
        }
    }
    @EventListener(GetRecentSearchLongEvent.class)
    public void get_realtime_issue(GetRecentSearchLongEvent recentSearchLongUuidEvent){



        List<RecentSearchShowDto> recentSearchDtos= redisUtills.return_realtime_issue();
        recentSearchLongUuidEvent.setRecentSearchDtos(recentSearchDtos);

    }


}
