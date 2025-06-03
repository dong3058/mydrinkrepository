package drinkselector.drinks.Serveices;


import drinkselector.drinks.Dtos.EmailAuthDto;
import drinkselector.drinks.Dtos.MailAuthCodeDto;
import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Enums.RedisKeyEnum;
import drinkselector.drinks.Etcs.Enums.RedisOpEnum;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Etcs.Jwts.Jwt;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.Etcs.RedisUtill.RedisOperationDto;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import drinkselector.drinks.Repository.MemberRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final RedisTemplate<String,String> redisTemplate;
    private final RedisUtills redisUtills;
    private final ApplicationEventPublisher publisher;
    private final MemberRepository memberRepository;
    private final JwtCreators jwtCreators;
    private final CookieUtils cookieUtils;

    @Value("${spring.mail.username}")
    private String sender;

    public ResponseEntity<ApiResponseCreator<String>> Sending_Auth_Code(MemberDto memberDto){

        String user_email=memberDto.getMember_mail();


        String auth_code=UUID.randomUUID().toString();


        publisher.publishEvent(new EmailAuthDto(user_email,auth_code));
        log.info("메일코드전송");
        return ResponseEntity.ok(ApiResponseCreator.success("성공", StateEnum.Success_Normally.getStates()));


    }


    public ResponseEntity<ApiResponseCreator<String>> Check_Auth_Code(MailAuthCodeDto mailAuthCodeDto,String ip){

        /* user_login_count
         * user_login_ip

         */
        Optional<String> auth_code=redisUtills.RedisValueGetOperation(mailAuthCodeDto.getMail());

        if(auth_code.isEmpty()){

            log.info("메일 코드 재전송");

            String code=UUID.randomUUID().toString();
            publisher.publishEvent(new EmailAuthDto(mailAuthCodeDto.getMail(),code));
            return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Normally.getStates()), HttpStatus.BAD_REQUEST);


        }

        if(auth_code.get().equals(mailAuthCodeDto.getAuth_code())&&auth_code.isPresent()){
            log.info("메일 코드 성공");


            Optional<Member> member=memberRepository.Check_User_Exist(mailAuthCodeDto.getMail());

            Long member_id=member.get().getMember_id();
            UserAdmin userAdmin=member.get().getUserAdmin();

            Jwt jwt=jwtCreators.gen_token(member_id,userAdmin);

            HttpHeaders httpHeaders=cookieUtils.Make_Header_With_Cookie(jwt.getAccess_token());


            List<RedisOperationDto> redisOperationDtos=new ArrayList<>();

            redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.HashSet, RedisKeyEnum.User_Login_Ip.getKey(),mailAuthCodeDto.getMail(),ip));
            redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.HashSet,RedisKeyEnum.User_Login_Count.getKey(),mailAuthCodeDto.getMail(),"0"));
            redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.ValueDelete, mailAuthCodeDto.getMail()));

            redisUtills.Make_Redis_Pipeline(redisOperationDtos);
            /*redisTemplate.executePipelined(new RedisCallback<Object>() {

                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisConnection redisConnection=(StringRedisConnection) connection;
                    redisTemplate.delete(mailAuthCodeDto.getMail());
                    redisTemplate.opsForHash().put("user_login_ip",mailAuthCodeDto.getMail(),ip);
                    redisTemplate.opsForHash().put("user_login_count",mailAuthCodeDto.getMail(),"0");

                     return null;
                }
            });*/


            return new ResponseEntity<>(ApiResponseCreator.success("로그인 성공",StateEnum.Success_Normally.getStates()),httpHeaders,HttpStatus.OK);


        }



        log.info("메일코드 그냥 실패");

        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Normally.getStates()), HttpStatus.BAD_REQUEST);




    }

}
