package drinkselector.drinks.security.detailservices;

import drinkselector.drinks.Etcs.Enums.Oauth2Enum;
import drinkselector.drinks.security.details.Oauth2Detail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Oauth2DetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Oauth2Enum oauth2Enum=Mapping_Oauth2(username);

        return new Oauth2Detail(oauth2Enum,username);
    }

    private Oauth2Enum Mapping_Oauth2(String url){

        if(url.contains("kakao")){
            log.info("mapping kakao success");

            return Oauth2Enum.Kakao;
        }
        else{

            return Oauth2Enum.Kakao;
        }

    }


}
