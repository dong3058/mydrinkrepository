package drinkselector.drinks.security.Kakao;


import drinkselector.drinks.Dtos.KakaoUserDataDto;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.Enums.MemberPlatForm;
import drinkselector.drinks.Etcs.Enums.Oauth2Enum;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Event.Events.Oauth2LogOutEvent;
import drinkselector.drinks.Repository.MemberRepository;
import drinkselector.drinks.security.Oauth2Solution;
import drinkselector.drinks.security.details.Oauth2Detail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class KakaoOauth2Solution implements Oauth2Solution {


    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;
    @Override
    public String Get_Access_Code(String query_str) {



        String [] split_str= query_str.split("=");
        log.info("쿼리 스트링:{}",split_str[1]);
        return split_str[1];
    }

    @Override
    public String Get_Access_Token(String Access_Code,Oauth2Detail oauth2Detail)  throws AuthenticationException{

        Oauth2Enum oauth2Enum=oauth2Detail.getOauth2Enum();

        String access_token_url=oauth2Enum.getRequest_access_token_url();

        RestTemplate restTemplate=new RestTemplate();


        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "aad9fffaff5b6655b991487c5ff8b445");
        body.add("redirect_uri", oauth2Enum.getRedirect_url()); // 인코딩 따로 안 해도 됨
        body.add("code", Access_Code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);


        ResponseEntity<KakaoAccessToken> response = restTemplate.postForEntity(access_token_url, requestEntity, KakaoAccessToken.class);


        if(response.getStatusCode().value()==200){
            log.info("response:{}---{}",response.getStatusCode(),response.getBody());
            //return "hello";
            KakaoAccessToken kakaoAccessToken=response.getBody();

            return kakaoAccessToken.getAccess_token();

        }

        throw new BadCredentialsException("잘못된 정보");
    }

    @Override
    public Member Get_User_Info(String access_token) throws AuthenticationException {


        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","Bearer %s".formatted(access_token));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserDataDto> response=restTemplate.exchange(Oauth2Enum.Kakao.getRequest_user_info_url(), HttpMethod.GET,entity, KakaoUserDataDto.class);

        if(response.getStatusCode().value()==200){
            KakaoUserDataDto kakaoUserDataDto=response.getBody();
            log.info("usermail:{}",kakaoUserDataDto.email());
            Optional<Member> member=memberRepository.Check_User_Exist(kakaoUserDataDto.email());
            if(member.isEmpty()) {

                Member member1 = new Member(kakaoUserDataDto.email(), "", UserAdmin.ROLE_User, MemberPlatForm.Kakao);
                LocalDateTime now = LocalDateTime.now();
                member1.setSign_in_date(now);
                member1.setUpdate_date(now);
                member = Optional.of(member1);
                memberRepository.save(member1);


            }
            else{
                if(member.get().getMemberPlatForm().name().equals("Normal")){
                    throw new BadCredentialsException("잘못된 정보");

                }

            }



            publisher.publishEvent(new Oauth2LogOutEvent(access_token,Oauth2Enum.Kakao));

            return member.get();



        }


        throw new BadCredentialsException("잘못된 정보");



    }
}
