package drinkselector.drinks.Configs;

import drinkselector.drinks.security.provider.MemberLoginProvider;
import drinkselector.drinks.security.provider.Oauth2AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Configuration
@RequiredArgsConstructor
public class AuthManager {

    private final MemberLoginProvider memberLoginProvider;
    private final Oauth2AuthProvider oauth2AuthProvider;
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(memberLoginProvider);
        //authenticationManagerBuilder.authenticationProvider(oauth2AuthProvider);
        return authenticationManagerBuilder.build();
    }
}
