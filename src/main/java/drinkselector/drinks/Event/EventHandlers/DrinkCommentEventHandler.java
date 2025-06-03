package drinkselector.drinks.Event.EventHandlers;


import drinkselector.drinks.Dtos.Update.DrinkCommentUpdateDto;
import drinkselector.drinks.Entity.DrinkComment;
import drinkselector.drinks.Entity.Drinks;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.TxtFilter;
import drinkselector.drinks.Event.Events.Save.DrinkCommentSaveEvent;
import drinkselector.drinks.Repository.DrinkCommentRepository;
import drinkselector.drinks.Repository.DrinkRepository;
import drinkselector.drinks.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DrinkCommentEventHandler {

    private final MemberRepository memberRepository;
    private final DrinkRepository drinkRepository;
    private final DrinkCommentRepository drinkCommentRepository;
    @Async
    @EventListener(DrinkCommentSaveEvent.class)
    public void drink_comment_save_event(DrinkCommentSaveEvent drinkCommentEvent){
        Optional<Member> member=memberRepository.findById(drinkCommentEvent.getMember_id());

        Optional<Drinks> drink=drinkRepository.findById(drinkCommentEvent.getDrink_id());


        String description= TxtFilter.file_txt_filter(drinkCommentEvent.getDescription());
        DrinkComment drinkComment=new DrinkComment(drink.get(),description,member.get());


        LocalDateTime now=LocalDateTime.now();
        drinkComment.setUpdate_date(now);

        drinkCommentRepository.save(drinkComment);


    }
    @EventListener(DrinkCommentUpdateDto.class)
    public void drink_comment_update_event(DrinkCommentUpdateDto drinkCommentUpdateDto){

        Optional<Member> member=memberRepository.findById(drinkCommentUpdateDto.getMember_id());

        Optional<DrinkComment> drinkComment=drinkCommentRepository.findById(drinkCommentUpdateDto.getComment_id());


        if(member.get().getMember_mail().equals(drinkCommentUpdateDto.getMember_name())){
            String description= TxtFilter.file_txt_filter(drinkCommentUpdateDto.getDescription());

            drinkComment.get().setComment_description(description);
            drinkCommentRepository.save(drinkComment.get());

            return ;
        }


        throw new RuntimeException();




    }
}
