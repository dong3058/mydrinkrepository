package drinkselector.drinks.Serveices;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Dtos.Save.DrinkCommentSaveDto;
import drinkselector.drinks.Dtos.MemberInfoDto;
import drinkselector.drinks.Entity.DrinkComment;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAdminService {



    private final ApplicationEventPublisher publisher;
    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    public ResponseEntity<ApiResponseCreator<MemberInfoDto>> Get_Member_Info(String member_name){


        String work_id= UUID.randomUUID().toString();
        publisher.publishEvent(new UserAdminEvent(member_name,work_id));
        try{
        Member member=objectMapper.readValue(redisTemplate.opsForValue().get(work_id),Member.class);


        return ResponseEntity.ok(ApiResponseCreator.success(new MemberInfoDto(member.getMember_id(),member.getMember_mail(),member.getUserAdmin(),member.getSign_in_date()),StateEnum.Success_Normally.getStates()));}

        catch (Exception e){


            throw new RuntimeException();
        }
    }

    public ResponseEntity<ApiResponseCreator<List<DrinkCommentSaveDto>>> Get_Member_Comment(Long member_id){

        String work_id= UUID.randomUUID().toString();
        publisher.publishEvent(new UserAdminEvent(member_id,work_id));
        try {
            List<DrinkComment> drinkComments = objectMapper.readValue(redisTemplate.opsForValue().get(work_id), new TypeReference<List<DrinkComment>>() {
            });

            List<DrinkCommentSaveDto> drinkCommentDtoList = drinkComments.stream().map(x -> {


                return new DrinkCommentSaveDto(x.getDrink().getDrink_id(), x.getComment_id(), x.getOther_drink_comment_id(),x.getWriter().getMember_id(), x.getWriter().getMember_mail(), x.getComment_description(), x.getSign_in_date());

            }).collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponseCreator.success(drinkCommentDtoList, StateEnum.Success_Normally.getStates()));

        }
        catch (Exception e){

            throw new RuntimeException();
        }

    }


    public ResponseEntity<ApiResponseCreator<UserAdmin>> Change_Member_Admin(Long member_id, UserAdmin admin){

        String work_id= UUID.randomUUID().toString();
        publisher.publishEvent(new UserAdminEvent(member_id,admin,work_id));
        try {
            Member member = objectMapper.readValue(redisTemplate.opsForValue().get(work_id), Member.class);
            return  ResponseEntity.ok(ApiResponseCreator.success(member.getUserAdmin(),StateEnum.Success_Normally.getStates()));
        }

        catch(Exception e){

            throw new RuntimeException();
        }


    }

}
