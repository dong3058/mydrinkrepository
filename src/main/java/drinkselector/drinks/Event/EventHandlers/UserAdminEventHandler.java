package drinkselector.drinks.Event.EventHandlers;


import drinkselector.drinks.Entity.Member;

import drinkselector.drinks.Etcs.Enums.RedisKeyEnum;

import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminChangeEvent;

import drinkselector.drinks.Event.Events.UserAdminEvent.UserAdminGetInfoEvent;

import drinkselector.drinks.Repository.MemberRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;

import org.springframework.stereotype.Component;


import java.util.Optional;


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



        redisUtills.RedisHashSetOperation(RedisKeyEnum.User_Admin.getKey(), String.valueOf(userAdminChangeEvent.member_id()),userAdminChangeEvent.userAdmin().name());
        memberRepository.Change_Member_Admin(userAdminChangeEvent.member_id(),userAdminChangeEvent.userAdmin());




    }




}
