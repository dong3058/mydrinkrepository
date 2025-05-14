package drinkselector.drinks.Etcs.Cookie;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CookieUtils {

    @Value("${jwt.expiration}")
    private long expiration;


    public HttpHeaders Make_Header_With_Cookie(String access_token){

        ResponseCookie responseCookie=ResponseCookie.from("access_token",access_token)
                .secure(true)
                .httpOnly(true)
                .sameSite("none")
                .maxAge(expiration+System.currentTimeMillis())
                .domain("localhost")
                .path("/")
                .build();


        HttpHeaders httpHeaders=new HttpHeaders();

        httpHeaders.add(HttpHeaders.SET_COOKIE,responseCookie.toString());



        return httpHeaders;

    }


    public ResponseCookie Make_Response_Cookie(String access_token){

        ResponseCookie responseCookie=ResponseCookie.from("access_token",access_token)
                .secure(true)
                .httpOnly(true)
                .sameSite("none")
                .maxAge(expiration+System.currentTimeMillis())
                .domain("localhost")
                .path("/")
                .build();


        return responseCookie;

    }


    public HttpHeaders Make_Header_With_Cookie_Logout(){

        ResponseCookie responseCookie=ResponseCookie.from("access_token","")
                .secure(true)
                .httpOnly(true)
                .sameSite("none")
                .maxAge(0)
                .domain("localhost")
                .path("/")
                .build();


        HttpHeaders httpHeaders=new HttpHeaders();

        httpHeaders.add(HttpHeaders.SET_COOKIE,responseCookie.toString());



        return httpHeaders;

    }






    public String Get_Token_From_Cookie(HttpServletRequest request){

       Cookie [] cookies= request.getCookies();

       if(cookies!=null) {
           Optional<Cookie> cookie = Arrays.stream(cookies).filter(x -> x.getName().equals("access_token"))
                   .findFirst();


           if(cookie.isPresent()){


               return cookie.get().getValue();
           }


           return null;


       }


       return null;
    }
}
