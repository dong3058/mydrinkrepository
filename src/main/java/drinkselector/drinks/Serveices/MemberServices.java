package drinkselector.drinks.Serveices;


import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Dtos.KakaoUserDataDto;
import drinkselector.drinks.Dtos.Oauth2Dto;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Enums.MemberPlatForm;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Etcs.Exceptions.ExistAccountError;
import drinkselector.drinks.Etcs.Jwts.Jwt;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.Etcs.RedisUtill.ReCentSearchLog;
import drinkselector.drinks.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServices {


    private final MemberRepository memberRepository;
    private final CookieUtils cookieUtils;
    private final JwtCreators jwtProviders;


    private final PasswordEncoder bCryptPasswordEncoder;







    public ResponseEntity<ApiResponseCreator<String>> Id_Exist_Check(String mail){
        Optional<Member> member_exist=memberRepository.Check_User_Exist(mail);


        if(member_exist.isPresent()){

            throw new ExistAccountError();
        }



        return ResponseEntity.ok(ApiResponseCreator.success("성공",StateEnum.Success_Normally.getStates()));


    }



    public ResponseEntity<ApiResponseCreator<String>> Member_Assign(String mail, String password){


        // String salt=BCrypt.gensalt();

        //String hashed_password=BCrypt.hashpw(password,salt);

        String hashed_password=bCryptPasswordEncoder.encode(password);

        Member member=new Member(mail,hashed_password, UserAdmin.ROLE_User, MemberPlatForm.Normal);
        LocalDateTime now=LocalDateTime.now();
        member.setSign_in_date(now);
        member.setUpdate_date(now);

        memberRepository.save(member);



        return ResponseEntity.ok(ApiResponseCreator.success("로그인성공", StateEnum.Success_Normally.getStates()));

    }

    public Long Member_Login2(String mail, String password){

        Optional<Member> member=memberRepository.Check_User_Exist(mail);
        if(member.isPresent()&&member.get().memberPlatForm.name().equals("Normal")){


            if(bCryptPasswordEncoder.matches(password,member.get().password)){


                /*Jwt jwt=jwtProviders.gen_token(member.get().getMember_id());

                HttpHeaders headers=cookieUtils.Make_Header_With_Cookie(jwt.getAccess_token());*/



              return member.get().getMember_id();

            }


        }



        return null;
    }

    public ResponseEntity<ApiResponseCreator<String>> Member_Login(String mail, String password){

        Optional<Member> member=memberRepository.Check_User_Exist(mail);
        if(member.isPresent()&&member.get().memberPlatForm.name().equals("Normal")){


            if(bCryptPasswordEncoder.matches(password,member.get().password)/*BCrypt.checkpw(password,member.get().password)*/){


                Jwt jwt=jwtProviders.gen_token(member.get().getMember_id(),member.get().getUserAdmin());

                HttpHeaders headers=cookieUtils.Make_Header_With_Cookie(jwt.getAccess_token());



                return new ResponseEntity<>(ApiResponseCreator.success("로그인 성공",StateEnum.Success_Normally.getStates()),headers,HttpStatus.OK);

            }


        }




        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_No_Exist_Member.getStates()),HttpStatus.BAD_REQUEST);

    }



    public ResponseEntity<ApiResponseCreator<String>> Oauth2Login(Oauth2Dto oauth2Dto){


        RestTemplate restTemplate=new RestTemplate();


        HttpHeaders headers=new HttpHeaders();

        headers.add("Authorization","Bearer %s".formatted(oauth2Dto.getAccess_code()));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserDataDto> response=restTemplate.exchange("https://kapi.kakao.com/v1/oidc/userinfo",HttpMethod.GET,entity, KakaoUserDataDto.class);

        if(response.getStatusCode().value()==200){
            KakaoUserDataDto kakaoUserDataDto=response.getBody();

            Optional<Member> member=memberRepository.Check_User_Exist(kakaoUserDataDto.getEmail());
            if(member.isEmpty()){

                Member member1=new Member(kakaoUserDataDto.getEmail(),"",UserAdmin.ROLE_User,MemberPlatForm.Kakao);
                LocalDateTime now=LocalDateTime.now();
                member1.setSign_in_date(now);
                member1.setUpdate_date(now);
                member=Optional.of(member1);
            }
            HttpHeaders headers2=new HttpHeaders();

            headers2.add("Authorization","Bearer %s".formatted(oauth2Dto.getAccess_code()));
            headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> entity2 = new HttpEntity<>(headers);

            ResponseEntity<String> response2=restTemplate.exchange("https://kapi.kakao.com/v1/user/logout",HttpMethod.POST ,entity2, String.class);



            HttpHeaders login_header=cookieUtils.Make_Header_With_Cookie(jwtProviders.gen_token(member.get().getMember_id(),UserAdmin.ROLE_User).getAccess_token());

            return new ResponseEntity<>(ApiResponseCreator.success("성공",StateEnum.Success_Normally.getStates()),login_header,HttpStatus.OK);
        }
        throw new RuntimeException();

    }

    public ResponseEntity<ApiResponseCreator<String>> Update_Member_Password(Long member_id, String password){


        Optional<Member> member=memberRepository.findById(member_id);

        if(Check_Member_Social_Login(member.get())){


        member.get().setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));


        return new ResponseEntity<>(ApiResponseCreator.success("success",StateEnum.Success_Normally.getStates()),HttpStatus.OK
        );}


        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Normally.getStates()),HttpStatus.BAD_REQUEST);


    }




    public ResponseEntity<ApiResponseCreator<String>> Member_LogOut(){


        return new ResponseEntity<>(ApiResponseCreator.success("성공적 로그아웃",StateEnum.Success_Normally.getStates()),cookieUtils.Make_Header_With_Cookie_Logout(),HttpStatus.OK);

    }


    private boolean Check_Member_Social_Login(Member member){

        if(member.getMemberPlatForm().name().equals("Normal")){
            return true;
        }


        return false;


    }
}
