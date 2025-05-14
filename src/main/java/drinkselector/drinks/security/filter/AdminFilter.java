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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Component
@RequiredArgsConstructor
public class AdminFilter extends OncePerRequestFilter {


    private final CookieUtils cookieUtils;

    private final JwtCreators jwtCreators;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final List<String> includepaths = List.of(
            "/admin/**"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path=request.getServletPath();
        return !includepaths.stream().anyMatch(pattern->pathMatcher.match(pattern,path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String access_token=cookieUtils.Get_Token_From_Cookie(request);

        JwtDataList jwtDataList =jwtCreators.Get_Data_From_Token(access_token);


        JwtMemberAuth jwtMemberAuth=new JwtMemberAuth(jwtDataList.getMember_id(),List.of(new SimpleGrantedAuthority(jwtDataList.getUser_admin())));


        SecurityContextHolder.getContext().setAuthentication(jwtMemberAuth);


        filterChain.doFilter(request,response);

    }
}
