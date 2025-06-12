package drinkselector.drinks.Serveices;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Dtos.Save.DrinkCommentSaveDto;
import drinkselector.drinks.Dtos.MemberInfoDto;
import drinkselector.drinks.Dtos.Show.DrinkCommentShowDto;
import drinkselector.drinks.Entity.DrinkComment;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminChangeEvent;

import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminGetInfoEvent;
import drinkselector.drinks.Repository.DrinkCommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserAdminService {



    private final ApplicationEventPublisher publisher;
    private final DrinkCommentRepository drinkCommentRepository;

    public ResponseEntity<ApiResponseCreator<MemberInfoDto>> Get_Member_Info(String member_name){



        UserAdminGetInfoEvent userAdminGetInfoEvent=new UserAdminGetInfoEvent(member_name);
        publisher.publishEvent(userAdminGetInfoEvent);
        try{
        Member member=userAdminGetInfoEvent.getMember();


        return ResponseEntity.ok(ApiResponseCreator.success(new MemberInfoDto(member.getMember_id(),member.getMember_mail(),member.getUserAdmin(),member.getSign_in_date()),StateEnum.Success_Normally.getStates()));}

        catch (Exception e){


            throw new RuntimeException();
        }
    }

    public ResponseEntity<ApiResponseCreator<List<DrinkCommentShowDto>>> Get_Member_Comment(Long member_id){

            List<DrinkComment> drinkComments=drinkCommentRepository.Get_Drink_Comment_List_By_Member(member_id);

            List<DrinkCommentShowDto> drinkCommentDtoList=drinkComments.stream()
                    .map(x->{

                       return new DrinkCommentShowDto(x.getComment_id(),x.getMember().getMember_mail(),x.getComment_description(),x.getUpdate_date());
                    }).toList();
            return ResponseEntity.ok(ApiResponseCreator.success(drinkCommentDtoList, StateEnum.Success_Normally.getStates()));




    }


    public ResponseEntity<ApiResponseCreator<String>> Change_Member_Admin(Long member_id,UserAdmin admin){

        log.info("admin:{}",admin);
        publisher.publishEvent(new UserAdminChangeEvent(member_id,admin));
        return new ResponseEntity<>(ApiResponseCreator.success("ok",StateEnum.Success_Normally.getStates()),HttpStatus.OK);



    }

}
