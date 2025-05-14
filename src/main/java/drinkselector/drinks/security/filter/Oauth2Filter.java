package drinkselector.drinks.security.filter;

import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Enums.Oauth2Enum;
import drinkselector.drinks.Etcs.Jwts.Jwt;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.security.authentication.Oauth2Auth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.AuthProvider;
import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class Oauth2Filter extends OncePerRequestFilter {


    private final AntPathMatcher antPathMatcher;

    private final JwtCreators jwtCreators;
    private final CookieUtils cookieUtils;

    private final AuthenticationManager authenticationManager;
    private final List<String> oauth2_redirect_url= List.of(Oauth2Enum.Kakao.getRedirect_url());

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url=request.getServletPath();
        return !oauth2_redirect_url.stream().anyMatch(pattern->pattern.contains(url));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path=request.getServletPath();
        log.info("path:{}",path);

        String query_str=request.getQueryString();
        Authentication oauthauth=new Oauth2Auth(path,query_str);


        oauthauth=authenticationManager.authenticate(oauthauth);
        Member member=(Member) oauthauth.getDetails();
        Jwt jwt=jwtCreators.gen_token(member.getMember_id(),member.getUserAdmin());

        ResponseCookie responseCookie=cookieUtils.Make_Response_Cookie(jwt.getAccess_token());

        response.addHeader(HttpHeaders.SET_COOKIE,responseCookie.toString());

        response.setStatus(200);
        response.sendRedirect("http://localhost:3000/home");

    }
}
