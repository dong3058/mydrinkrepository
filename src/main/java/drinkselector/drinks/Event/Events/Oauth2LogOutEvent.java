package drinkselector.drinks.Event.Events;


import drinkselector.drinks.Etcs.Enums.Oauth2Enum;
import lombok.AllArgsConstructor;
import lombok.Data;


public record Oauth2LogOutEvent(String access_code,Oauth2Enum oauth2Enum) {



}
