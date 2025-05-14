package drinkselector.drinks.Serveices;


import drinkselector.drinks.Dtos.MailAuthCodeDto;
import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.State;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final RedisTemplate<String,String> redisTemplate;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public ResponseEntity<ApiResponseCreator<String>> Sending_Auth_Code(MemberDto memberDto){

        String user_email=memberDto.getMember_mail();


        String auth_code=UUID.randomUUID().toString();

        MimeMessage message=javaMailSender.createMimeMessage();


        try {
            message.setFrom(sender);
            message.setRecipients(MimeMessage.RecipientType.TO, user_email);
            message.setText(auth_code, "UTF-8", "plain");
            javaMailSender.send(message);

            redisTemplate.opsForValue().set(user_email,auth_code,2, TimeUnit.MINUTES);



            return ResponseEntity.ok(ApiResponseCreator.success("성공", StateEnum.Success_Normally.getStates()));

        }
        catch (Exception e){



            throw new RuntimeException();
        }


    }


    public ResponseEntity<ApiResponseCreator<String>> Check_Auth_Code(MailAuthCodeDto mailAuthCodeDto){

        Optional<String> auth_code=Optional.ofNullable(redisTemplate.opsForValue().get(mailAuthCodeDto.getMail()));


        if(auth_code.get().equals(mailAuthCodeDto.getAuth_code())&&auth_code.isPresent()){



            redisTemplate.delete(mailAuthCodeDto.getMail());
            return ResponseEntity.ok(ApiResponseCreator.success("성공",StateEnum.Success_Normally.getStates()));
        }





        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Normally.getStates()), HttpStatus.BAD_REQUEST);




    }

}
