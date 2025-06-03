package drinkselector.drinks.Event.EventHandlers;


import drinkselector.drinks.Dtos.EmailAuthDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nd4j.linalg.api.ops.Op;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailAuthSendEvent {

    private final JavaMailSender javaMailSender;



    private final RedisUtills redisUtills;
    @Value("${spring.mail.username}")
    private String sender;
    @Async

    @EventListener(EmailAuthDto.class)
    public void MailAuthCodeSendEvent(EmailAuthDto emailAuthDto){


        MimeMessage message=javaMailSender.createMimeMessage();
        String auth_code=emailAuthDto.getAuth_code();
        String user_email=emailAuthDto.getUser_mail();



        try {

            Optional<String> code=redisUtills.RedisValueGetOperation(user_email);

            if( code.isEmpty()) {

                message.setFrom(sender);
                message.setRecipients(MimeMessage.RecipientType.TO, user_email);
                message.setText(auth_code, "UTF-8", "plain");
                javaMailSender.send(message);


                redisUtills.RedisValueSetOperation(user_email,auth_code,2L,TimeUnit.MINUTES);


            }

        }
        catch (Exception e){
            log.info("error:{}",e);
            throw new RuntimeException();
        }

    }

}
