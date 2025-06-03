package drinkselector.drinks.Event.Events;


import drinkselector.drinks.Etcs.Enums.Oauth2Enum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Oauth2LogOutEvent {

    private String access_code;

    private Oauth2Enum oauth2Enum;


}
