package drinkselector.drinks.Event.EventHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminEvent;
import drinkselector.drinks.Repository.DrinkCommentRepository;
import drinkselector.drinks.Repository.MemberRepository;
import drinkselector.drinks.Serveices.DrinkDescriptionService;
import drinkselector.drinks.Serveices.MemberServices;
import lombok.RequiredArgsConstructor;
import org.nd4j.shade.protobuf.Api;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class UserAdminEventHandler {


    private final MemberRepository memberRepository;
    private final DrinkCommentRepository drinkCommentRepository;
    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    @EventListener(UserAdminEvent.class)
    public void User_Admin_Need_Member_Service(UserAdminEvent userAdminEvent){
        try {
            if (userAdminEvent.getMember_name() != null) {
                Optional<Member> member = memberRepository.Check_User_Exist(userAdminEvent.getMember_name());
                if (member.isEmpty()) {
                    throw new RuntimeException();
                }

                redisTemplate.opsForValue().set(userAdminEvent.getRedis_work_id(), objectMapper.writeValueAsString(member.get()),60, TimeUnit.SECONDS);

            }
            else{
               if( userAdminEvent.getMember_name() == null & userAdminEvent.getUserAdmin() == null) {
                   redisTemplate.opsForValue().set(userAdminEvent.getRedis_work_id(), objectMapper.writeValueAsString(drinkCommentRepository.Get_Drink_Comment_List_By_Member(userAdminEvent.getMember_id())),60,TimeUnit.SECONDS);
                   }
               else{
                     redisTemplate.opsForValue().set(userAdminEvent.getRedis_work_id(), objectMapper.writeValueAsString(memberRepository.Change_Member_Admin(userAdminEvent.getMember_id(), userAdminEvent.getUserAdmin()).get()),60,TimeUnit.SECONDS);}
            }
        }
        catch (Exception e){

            throw new RuntimeException();
        }

    }




}
