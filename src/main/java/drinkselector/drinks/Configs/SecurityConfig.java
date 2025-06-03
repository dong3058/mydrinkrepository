package drinkselector.drinks.Configs;


import drinkselector.drinks.security.AccessDenied;
import drinkselector.drinks.security.EntryPointHandler;


import drinkselector.drinks.security.filter.CookieFilter;
import drinkselector.drinks.security.filter.InitLoginFilter;
import drinkselector.drinks.security.filter.Oauth2Filter;
import drinkselector.drinks.security.provider.MemberLoginProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final AccessDenied accessDenied;
    private final EntryPointHandler entryPointHandler;
    private final InitLoginFilter initLoginFilter;
    private final CookieFilter cookieFilter;
    private final Oauth2Filter oauth2Filter;
    private final AuthenticationManager authenticationManager;





    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(x->x.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex->ex.accessDeniedHandler(accessDenied)
                .authenticationEntryPoint(entryPointHandler))
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/admin/**").hasRole("Admin")
                        .requestMatchers( "/user_find/**", "/comment/save/**","/comment/update/**" ,"/member/change")
                        .hasAnyRole("Admin","User")
                        .requestMatchers("/drink/update","/drink/save")
                        .hasRole("Admin")
                        .anyRequest().permitAll())
                .addFilterAt(initLoginFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(cookieFilter,BasicAuthenticationFilter.class)
                .addFilterAfter(oauth2Filter,BasicAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config=new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }




}


