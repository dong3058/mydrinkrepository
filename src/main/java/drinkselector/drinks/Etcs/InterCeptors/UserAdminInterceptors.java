package drinkselector.drinks.Etcs.InterCeptors;

import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Etcs.Exceptions.AdminError;
import drinkselector.drinks.Etcs.Exceptions.ReLoginError;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.Repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserAdminInterceptors implements HandlerInterceptor {



    private final CookieUtils cookieUtils;

    private final JwtCreators jwtProviders;


    private final MemberRepository memberRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String access_token=cookieUtils.Get_Token_From_Cookie(request);


        if(jwtProviders.Valid_Jwt(access_token)){

            Long member_id=jwtProviders.Get_Member_Id_From_Jwt(access_token);

            Optional<Member> member=memberRepository.findById(member_id);

           if(member.isPresent()){

               return Check_User_Admin(member.get().getUserAdmin());
           }


        }

        throw new ReLoginError();

    }


    private boolean Check_User_Admin(UserAdmin userAdmin){


        if(userAdmin.name().equals("User_Admin")){

            return true;
        }

        throw new AdminError();
    }


}
