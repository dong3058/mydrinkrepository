package drinkselector.drinks.Event.EventHandlers;


import drinkselector.drinks.Etcs.Enums.Oauth2Enum;
import drinkselector.drinks.Event.Events.Oauth2LogOutEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Oauth2LogOutEventHandler {


    @Async
    @EventListener(Oauth2LogOutEvent.class)
    public void Logout(Oauth2LogOutEvent oauth2LogOutEvent){


        Oauth2Enum oauth2Enum=oauth2LogOutEvent.oauth2Enum();
        if(oauth2Enum.name().equals("Kakao")) {
            RestTemplate restTemplate=new RestTemplate();
            HttpHeaders headers2 = new HttpHeaders();
            headers2.add("Authorization", "Bearer %s".formatted());
            headers2.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> entity2 = new HttpEntity<>(headers2);
            ResponseEntity<String> response2 = restTemplate.exchange("https://kapi.kakao.com/v1/user/logout", HttpMethod.POST, entity2, String.class);
        }


    }




}
