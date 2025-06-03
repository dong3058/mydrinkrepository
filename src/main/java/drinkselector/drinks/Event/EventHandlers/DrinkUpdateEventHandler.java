package drinkselector.drinks.Event.EventHandlers;


import drinkselector.drinks.Entity.DrinkDescription;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Event.Events.Update.DrinkUpdateEvent;
import drinkselector.drinks.Event.Events.Save.DrinkUploadEvent;
import drinkselector.drinks.Repository.MemberRepository;
import drinkselector.drinks.Serveices.DrinkDescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DrinkUpdateEventHandler {



    private final MemberRepository memberRepository;

    private final DrinkDescriptionService drinkDescriptionService;

    @Async
    @EventListener(DrinkUploadEvent.class)
    public void Drink_Description_Save_Event(DrinkUploadEvent drinkUploadEvent){



        String description=drinkDescriptionService.Filtering_Text(drinkUploadEvent.getUser_description());

        Optional<Member> member=memberRepository.findById(drinkUploadEvent.getMember_id());


        DrinkDescription drinkDescription=new DrinkDescription(description,drinkUploadEvent.getDrink(),member.get());
        log.info("save전까지 잘왓음");
        drinkDescriptionService.Save_Drink_Description(drinkDescription);


    }



    @EventListener(DrinkUpdateEvent.class)
    public void Drink_Update_Event(DrinkUpdateEvent drinkUpdateEvent){

        String description=drinkDescriptionService.Filtering_Text(drinkUpdateEvent.getUser_description());

        drinkDescriptionService.Update_Drink_Description(drinkUpdateEvent.getDrink_id(),description);


    }

}
