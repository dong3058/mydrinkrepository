package drinkselector.drinks.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Etcs.Jwts.Jwt;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.security.authentication.MemberLoginAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ConcreteProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class InitLoginFilter extends OncePerRequestFilter {


    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtCreators jwtCreators;
    private final CookieUtils cookieUtils;


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

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
        String password=jsonNode.get("password").asText();

        Authentication authentication=new MemberLoginAuthentication(email,password);

        authentication=authenticationManager.authenticate(authentication);


        Jwt jwt=jwtCreators.gen_token((Long) authentication.getPrincipal(), UserAdmin.ROLE_User);

        ResponseCookie responseCookie =cookieUtils.Make_Response_Cookie(jwt.getAccess_token());


        response.addHeader(HttpHeaders.SET_COOKIE,responseCookie.toString());

        response.setStatus(200);

        //return new ResponseEntity<>(ApiResponseCreator.success("로그인 성공",StateEnum.Success_Normally.getStates()),headers,HttpStatus.OK);







    }
}
