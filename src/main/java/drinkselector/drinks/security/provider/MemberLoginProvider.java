package drinkselector.drinks.security.provider;

import drinkselector.drinks.Serveices.MemberServices;
import drinkselector.drinks.security.authentication.MemberLoginAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberLoginProvider implements AuthenticationProvider {

    private final MemberServices memberServices;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email=authentication.getName();
        String password=(String) authentication.getCredentials();

        Long member_id=memberServices.Member_Login2(email,password);
        if(member_id!=null){


            return new UsernamePasswordAuthenticationToken(member_id,"");
        }


        throw new BadCredentialsException("잘못된 정보");
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return MemberLoginAuthentication.class.isAssignableFrom(authentication);
    }
}
