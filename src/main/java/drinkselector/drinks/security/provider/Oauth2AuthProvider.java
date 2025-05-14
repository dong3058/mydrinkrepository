package drinkselector.drinks.security.provider;

import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.security.Oauth2SolutionProvider;
import drinkselector.drinks.security.authentication.MemberAuth;
import drinkselector.drinks.security.authentication.Oauth2Auth;
import drinkselector.drinks.security.details.Oauth2Detail;
import drinkselector.drinks.security.detailservices.Oauth2DetailService;
import jakarta.security.auth.message.config.AuthConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Oauth2AuthProvider implements AuthenticationProvider {



    private final Oauth2DetailService oauth2DetailService;
    private final Oauth2SolutionProvider oauth2SolutionProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        UserDetails oauth2Detail=oauth2DetailService.loadUserByUsername(authentication.getName());


        Member member=oauth2SolutionProvider.Resolve_Oauth2_Sol((Oauth2Detail) oauth2Detail,(String) authentication.getDetails());


        return new MemberAuth(member);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Oauth2Auth.class.isAssignableFrom(authentication);
    }
}
