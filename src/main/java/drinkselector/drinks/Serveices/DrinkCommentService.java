package drinkselector.drinks.Serveices;


import drinkselector.drinks.Dtos.Save.DrinkCommentSaveDto;
import drinkselector.drinks.Dtos.Show.DrinkCommentShowDto;
import drinkselector.drinks.Dtos.Update.DrinkCommentUpdateDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Event.Events.Save.DrinkCommentSaveEvent;
import drinkselector.drinks.Repository.DrinkCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkCommentService {



    private final ApplicationEventPublisher publisher;

    private final DrinkCommentRepository drinkCommentRepository;

    public ResponseEntity<ApiResponseCreator<String>> Save_DrinkComment(Long member_id, DrinkCommentSaveDto drinkCommentDto){



        publisher.publishEvent(new DrinkCommentSaveEvent(member_id, drinkCommentDto.getDrink_id(),drinkCommentDto.getComment_description()));



        return ResponseEntity.ok(ApiResponseCreator.success("success", StateEnum.Success_Normally.getStates()));


    }
    public ResponseEntity<ApiResponseCreator<List<DrinkCommentShowDto>>> Get_Drink_List(Long drink_id){

        List<DrinkCommentShowDto> drinkCommentShowDtos=drinkCommentRepository.Get_Drink_Comment_List(drink_id);

        return ResponseEntity.ok(ApiResponseCreator.success(drinkCommentShowDtos,StateEnum.Success_Normally.getStates()));


    }

    public ResponseEntity<ApiResponseCreator<String>> Update_Drink_Comment(Long member_id, DrinkCommentUpdateDto drinkCommentUpdateDto){



        publisher.publishEvent(new DrinkCommentUpdateDto(drinkCommentUpdateDto.getComment_id(),drinkCommentUpdateDto.getMember_name(),drinkCommentUpdateDto.getDescription(),member_id));



        return  ResponseEntity.ok(ApiResponseCreator.success("success",StateEnum.Success_Normally.getStates()));

    }

}
