package drinkselector.drinks.Etcs.InterCeptors;

import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Exceptions.ReLoginError;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;



@RequiredArgsConstructor
public class LoginInterCeptors implements HandlerInterceptor {




    private final CookieUtils cookieUtils;

    private final JwtCreators jwtProviders;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        if(isPreflightRequest(request)){


            return true;
        }



        return Valid_Request_Token(request);


    }


    private boolean Valid_Request_Token(HttpServletRequest request){
        String token=cookieUtils.Get_Token_From_Cookie(request);

        if(token!=null){

            return jwtProviders.Valid_Jwt(token);


        }


        throw new ReLoginError();

    }


    private boolean isPreflightRequest(HttpServletRequest request) {
        return isOptions(request) && hasHeaders(request) && hasMethod(request) && hasOrigin(request);
    }

    private boolean isOptions(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString());
    }

    private boolean hasHeaders(HttpServletRequest request) {
        return (request.getHeader("Access-Control-Request-Headers"))!=null;
    }

    private boolean hasMethod(HttpServletRequest request) {
        return (request.getHeader("Access-Control-Request-Method"))!=null;
    }

    private boolean hasOrigin(HttpServletRequest request) {
        return (request.getHeader("Origin"))!=null;
    }


}
