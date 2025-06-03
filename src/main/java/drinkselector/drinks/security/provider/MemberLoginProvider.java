package drinkselector.drinks.security.provider;

import drinkselector.drinks.Entity.Member;

import drinkselector.drinks.Etcs.Enums.RedisKeyEnum;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import drinkselector.drinks.Serveices.MemberServices;
import drinkselector.drinks.security.authentication.MemberAuth;

import drinkselector.drinks.security.authentication.MemberLoginAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberLoginProvider implements AuthenticationProvider {

    private final MemberServices memberServices;
    private final RedisTemplate<String,String> redisTemplate;
    private final RedisUtills redisUtills;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email=authentication.getName();
        String password=(String) authentication.getCredentials();
        String ip=(String) authentication.getDetails();
        Optional<Member> member=memberServices.Member_Login2(email,password);

        if(member.isPresent()){
            Member m=member.get();

            if(m.getPassword().equals("")){
                /* user_login_count
                 * user_login_ip

                 */

                Long count=Long.parseLong(redisUtills.RedisHashGetOperation(RedisKeyEnum.User_Login_Count.getKey(),email).get());

                if(5>count){

                    count+=1;

                    redisUtills.RedisHashSetOperation(RedisKeyEnum.User_Login_Count.getKey(),email,String.valueOf(count));

                }

                throw new BadCredentialsException("잘못된 정보");
            }

            else {
                Long member_id = m.getMember_id();
                Long count=Long.parseLong(redisUtills.RedisHashGetOperation(RedisKeyEnum.User_Login_Count.getKey(),email).get());

                if(count>=5L){
                    return new MemberAuth(m);
                    //return new UsernamePasswordAuthenticationToken(member_id,m.getUserAdmin());
                    //기본으로 구현된 이 인증객체는 credentials를 자동으로 null로바꿈. 직접 구현한애는 안그럼.
                }

                count+=1;
                redisUtills.RedisHashSetOperation(RedisKeyEnum.User_Login_Count.getKey(),email,String.valueOf(count));



                return new MemberAuth(m);
               /// return new UsernamePasswordAuthenticationToken(member_id,"진짜없냐?");
            }
        }
        else {
            log.info("계정 자체가 없는 케이스");

            throw new BadCredentialsException("잘못된 정보");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return MemberLoginAuthentication.class.isAssignableFrom(authentication);
    }
}
