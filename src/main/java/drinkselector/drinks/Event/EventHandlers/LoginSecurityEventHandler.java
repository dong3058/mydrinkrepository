package drinkselector.drinks.Event.EventHandlers;


import drinkselector.drinks.Dtos.EmailAuthDto;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import drinkselector.drinks.Event.Events.LoginSecurityEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginSecurityEventHandler {


    private final ApplicationEventPublisher eventPublisher;
    private final RedisUtills redisUtills;

    @EventListener(LoginSecurityEvent.class)
    public void SecurityLoginCheck(LoginSecurityEvent loginSecurityEvent){
        String ip = loginSecurityEvent.getIp();
        String mail = loginSecurityEvent.getMail();
        log.info("보안설정:{}",redisUtills.Check_Security_Login_Require(ip,mail));
        if(redisUtills.Check_Security_Login_Require(ip,mail)){
            log.info("보안설정에걸림");
            String uuid= UUID.randomUUID().toString();
            eventPublisher.publishEvent(new EmailAuthDto(mail,uuid));
            throw new RuntimeException("로그인 보안설정에 걸림");
        }
        else{
            redisUtills.Init_Security_Login_Check(mail);


        }



    }




}
