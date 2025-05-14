package drinkselector.drinks.Configs;


import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.CustomAnnotations.LoginAnnotationResolver;
import drinkselector.drinks.Etcs.InterCeptors.LoginInterCeptors;
import drinkselector.drinks.Etcs.InterCeptors.UserAdminInterceptors;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtCreators jwtProviders;
    private final CookieUtils cookieUtils;
    private final MemberRepository memberRepository;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("**")
                .allowedOriginPatterns("http://localhost:3000");
    }

    @Bean
    public AntPathMatcher antPathMatcher(){


        return new AntPathMatcher();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterCeptors(cookieUtils,jwtProviders))
                .order(1)
                .addPathPatterns("*");
        registry.addInterceptor(new UserAdminInterceptors(cookieUtils,jwtProviders,memberRepository))
                .order(2)
                .addPathPatterns("*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginAnnotationResolver(cookieUtils,jwtProviders));
    }
}
