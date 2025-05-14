package drinkselector.drinks.security;


import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.Enums.Oauth2Enum;
import drinkselector.drinks.security.Kakao.KakaoOauth2Solution;
import drinkselector.drinks.security.authentication.Oauth2Auth;
import drinkselector.drinks.security.details.Oauth2Detail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class Oauth2SolutionProvider {


    private final KakaoOauth2Solution kakaoOauth2Solution;




    public Member Resolve_Oauth2_Sol(Oauth2Detail oauth2Detail,String query_str) throws AuthenticationException {

       if(oauth2Detail.getOauth2Enum().name().equals("Kakao")){
           log.info("name kakao");
           String code=kakaoOauth2Solution.Get_Access_Code(query_str);
           String token=kakaoOauth2Solution.Get_Access_Token(code,oauth2Detail);
           Member user=kakaoOauth2Solution.Get_User_Info(token);

           return user;
       }


        return null;

    }


}
