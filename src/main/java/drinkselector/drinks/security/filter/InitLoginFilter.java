package drinkselector.drinks.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Jwts.Jwt;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.Event.Events.LoginSecurityEvent;
import drinkselector.drinks.security.authentication.MemberLoginAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitLoginFilter extends OncePerRequestFilter {



    private final AuthenticationManager authenticationManager;
    private final JwtCreators jwtCreators;
    private final CookieUtils cookieUtils;
    private final ApplicationEventPublisher publisher;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/member/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            log.info("infilter");
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String body = sb.toString();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(body);
            String email = jsonNode.get("member_mail").asText();
            String password = jsonNode.get("password").asText();


            String ip = request.getRemoteAddr();

            Authentication authentication = new MemberLoginAuthentication(email, password, ip);

            authentication = authenticationManager.authenticate(authentication);

            publisher.publishEvent(new LoginSecurityEvent(email, ip));
            Member m=(Member) authentication.getDetails();

            Jwt jwt = jwtCreators.gen_token(m.getMember_id(),m.getUserAdmin());

            ResponseCookie responseCookie = cookieUtils.Make_Response_Cookie(jwt.getAccess_token());

            response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

            response.setStatus(200);
        }
        catch (Exception e){

            response.setStatus(466);
        }
        //return new ResponseEntity<>(ApiResponseCreator.success("로그인 성공",StateEnum.Success_Normally.getStates()),headers,HttpStatus.OK);







    }
}
