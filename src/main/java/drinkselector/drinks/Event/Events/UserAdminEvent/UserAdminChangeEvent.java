package drinkselector.drinks.Event.Events.UserAdminEvent;

import drinkselector.drinks.Etcs.Enums.UserAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;


public record UserAdminChangeEvent(Long member_id,UserAdmin userAdmin) {


}
