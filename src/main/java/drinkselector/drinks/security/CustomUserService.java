package drinkselector.drinks.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CustomUserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("oauth2user:{}",oAuth2User.toString());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Oauth2Response  oAuth2Response= createOAuth2Response(registrationId, oAuth2User.getAttributes());

        log.info("user-data:{}",oAuth2Response.getEmail());
        log.info("user-data:{}",oAuth2Response.getNickname());
        log.info("user-data:{}",oAuth2Response.getProviderId());
        return null;

    }


    private Oauth2Response createOAuth2Response(String registrationId, Map<String, Object> attributes) {
        log.info("registrationid:{}",registrationId);
        return new KakaoResponse(attributes);
    }

}
