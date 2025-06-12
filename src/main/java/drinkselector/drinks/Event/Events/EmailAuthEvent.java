package drinkselector.drinks.Event.Events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record EmailAuthEvent(String user_mail,String auth_code) {

}
