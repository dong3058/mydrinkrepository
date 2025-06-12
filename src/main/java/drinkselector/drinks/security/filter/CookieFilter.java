package drinkselector.drinks.security.filter;

import drinkselector.drinks.Dtos.JwtDataList;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.security.authentication.JwtMemberAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CookieFilter extends OncePerRequestFilter {

    private final CookieUtils cookieUtils;

    private final JwtCreators jwtCreators;


    private final AntPathMatcher antPathMatcher;


    private final List<String> includepaths = List.of(
            "/drink/search/**","/user_find/**","/drink/update","/drink/save", "/comment/save/**","/comment/update/**" ,"/member/change","/admin/**"

    );



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri=request.getServletPath();
        return !includepaths.stream().anyMatch(pattern->antPathMatcher.match(pattern,uri));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String access_token=cookieUtils.Get_Token_From_Cookie(request);

        log.info("Access_token:{}",access_token);
        log.info("경로:{}",request.getServletPath());
        if(access_token==null){

            if(request.getServletPath().contains("/drink/search/")){
                log.info("토큰없는대 검색경로인경우");
                filterChain.doFilter(request,response);
            }
            response.setStatus(400);

        }
        try{
            JwtDataList jwtDataList=jwtCreators.Get_Data_From_Token(access_token);
            log.info("data:{}",jwtDataList);
            Authentication jwtauth=new JwtMemberAuth(jwtDataList.member_id(),List.of(new SimpleGrantedAuthority(jwtDataList.user_admin())));
            log.info("jwtauth:{}",jwtauth.getAuthorities());
            log.info("jwtauth:{}",jwtauth.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(jwtauth);


            log.info("마무릮ㄲ<ㅌ");

            filterChain.doFilter(request,response);

        }
        catch (Exception e){

            response.setStatus(400);
        }
    }
}
