package drinkselector.drinks.Etcs.CustomAnnotations;

import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class LoginAnnotationResolver implements HandlerMethodArgumentResolver {



    private final CookieUtils cookieUtils;
    private final JwtCreators jwtProviders;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {




        return parameter.getParameterAnnotation(LoginUser.class) !=null && parameter.getParameterType().equals(long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

       Authentication authentication= SecurityContextHolder.getContext().getAuthentication();


       return (Long) authentication.getPrincipal();
        /*HttpServletRequest request=(HttpServletRequest) webRequest.getNativeRequest();

        String access_token=cookieUtils.Get_Token_From_Cookie(request);


        if(access_token==null){

            return null;

        }

        return jwtProviders.Get_Member_Id_From_Jwt(access_token);*/


    }
}
