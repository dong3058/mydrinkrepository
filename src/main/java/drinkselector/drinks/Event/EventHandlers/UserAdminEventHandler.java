package drinkselector.drinks.Event.EventHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Enums.RedisKeyEnum;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminChangeEvent;
import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminEvent;
import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminGetInfoEvent;
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
    private final RedisUtills redisUtills;





    @EventListener(UserAdminGetInfoEvent.class)
    public void User_Admim_Get_Info(UserAdminGetInfoEvent userAdminGetInfoEvent){
        Optional<Member> member=memberRepository.Check_User_Exist(userAdminGetInfoEvent.getMember_name());

        if (member.isPresent()){
            try {

                userAdminGetInfoEvent.setMember(member.get());
            }
            catch (Exception e){

                throw new RuntimeException();
            }
        }
        else{
            throw new RuntimeException();
        }


    }
    @EventListener(UserAdminChangeEvent.class)
    public void User_Admin_Change_Event(UserAdminChangeEvent userAdminChangeEvent){



        redisUtills.RedisHashSetOperation(RedisKeyEnum.User_Admin.getKey(), String.valueOf(userAdminChangeEvent.getMember_id()),userAdminChangeEvent.getUserAdmin().name());
        memberRepository.Change_Member_Admin(userAdminChangeEvent.getMember_id(),userAdminChangeEvent.getUserAdmin());




    }




}
