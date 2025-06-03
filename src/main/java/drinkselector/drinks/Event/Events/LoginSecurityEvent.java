package drinkselector.drinks.Event.Events;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSecurityEvent {


    private String mail;
    private String ip;

}
