package drinkselector.drinks.Serveices;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Dtos.*;
import drinkselector.drinks.Dtos.Save.DrinkSaveDto;
import drinkselector.drinks.Dtos.Show.DrinkSearchShowDto;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Dtos.Update.DrinkUpdateDto;
import drinkselector.drinks.Entity.Drinks;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.DrinkType;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.PagingEntity;
import drinkselector.drinks.Etcs.RedisUtill.ReCentSearchLog;
import drinkselector.drinks.Event.Events.Update.DrinkUpdateEvent;
import drinkselector.drinks.Event.Events.Save.DrinkUploadEvent;
import drinkselector.drinks.Event.Events.RecentSearchLogEvent;
import drinkselector.drinks.Event.Events.GetRecentSearchLongEvent;
import drinkselector.drinks.Repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrinkService {


    private final DrinkRepository drinkRepository;
    private final ApplicationEventPublisher publisher;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String,String> redisTemplate;
    private final ReCentSearchLog reCentSearchLog;



    public ResponseEntity<ApiResponseCreator<String>> Save_Drink_Info(DrinkSaveDto userMadeDescriptionDto, Long member_id){

        Drinks drinks=new Drinks(DrinkType.valueOf(userMadeDescriptionDto.getDrink_type()),userMadeDescriptionDto.getDrink_name());
        LocalDateTime now=LocalDateTime.now();
        drinks.setSign_in_date(now);
        drinks.setUpdate_date(now);
        drinkRepository.save(drinks);



        publisher.publishEvent(new DrinkUploadEvent(member_id,drinks,userMadeDescriptionDto.getDescription()));



        return ResponseEntity.ok(ApiResponseCreator.success("성공",StateEnum.Success_Normally.getStates()));



    }


    public ResponseEntity<ApiResponseCreator<String>> Update_Drink_Info(DrinkUpdateDto drinkUpdateDto, Long drink_id) {

        publisher.publishEvent(new DrinkUpdateEvent(drink_id, drinkUpdateDto.getDescription()));

        return ResponseEntity.ok(ApiResponseCreator.success("success",StateEnum.Success_Normally.getStates()));


    }



    public ResponseEntity<ApiResponseCreator<List<RecentSearchShowDto>>> get_hot_issue(){


        String work_id= UUID.randomUUID().toString();
        publisher.publishEvent(new GetRecentSearchLongEvent(work_id));
        try {
           List<RecentSearchShowDto> recentSearchDtos=objectMapper.readValue(redisTemplate.opsForValue().get(work_id), new TypeReference<List<RecentSearchShowDto>>() {
            });

            return ResponseEntity.ok(ApiResponseCreator.success(recentSearchDtos, StateEnum.Success_Normally.getStates()));
        }
        catch (Exception e){
            throw new RuntimeException();
        }

    }

    public ResponseEntity<ApiResponseCreator<DrinkDto>> Get_Drink_Info(Long drink_id,String drink_name,Long member_id){


       Optional<DrinkDto> drinkDto= drinkRepository.Get_Drink_Info(drink_id);

       publisher.publishEvent(new RecentSearchLogEvent(drink_id,drink_name));

       if(drinkDto.isPresent()){

           if(member_id!=null){
               publisher.publishEvent(new RecentSearchLogEvent(member_id,drink_id,drink_name));

               Long size=redisTemplate.opsForSet().size("%s".formatted(drink_id));

               drinkDto.get().setLike_number(size);

           }


       return ResponseEntity.ok(ApiResponseCreator.success(drinkDto.get(),StateEnum.Success_Normally.getStates()));}



       throw new RuntimeException();
    }


    public ResponseEntity<ApiResponseCreator<List<DrinkSearchShowDto>>> get_name_likes_drink(String drink_name){

        Pageable pageable=PageRequest.of(0,5);

        List<DrinkSearchShowDto> drinkDtos=drinkRepository.Get_Name_Likes_Drink(pageable,drink_name);

        return ResponseEntity.ok(ApiResponseCreator.success(drinkDtos,StateEnum.Success_Normally.getStates()));


    }


    public ResponseEntity<ApiResponseCreator<DrinkDto>> Get_Random_Drink(){



       Map.Entry<Object,Object> entry= redisTemplate.opsForHash().randomEntry("drink");


       Optional<DrinkDto> drink= drinkRepository.Get_Drink_Info((Long) entry.getValue());



       return ResponseEntity.ok(ApiResponseCreator.success(drink.get(),StateEnum.Success_Normally.getStates()));


    }



    public ResponseEntity<ApiResponseCreator<List<DrinkSearchShowDto>>> Get_Drink_By_Category(String category, PagingEntity pagingEntity){


        Pageable p= PageRequest.of(pagingEntity.getPage(),pagingEntity.getSize());


        Page<DrinkSearchShowDto> drinkSearchDtoPage=drinkRepository.Get_Drink_By_Category(p,category);



        return ResponseEntity.ok(ApiResponseCreator.success(drinkSearchDtoPage.get().collect(Collectors.toList()), StateEnum.Success_Normally.getStates()));


    }



}
