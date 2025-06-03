package drinkselector.drinks.Event.Events.UserAdminEvent;

import drinkselector.drinks.Etcs.Enums.UserAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;

@Data

public class UserAdminChangeEvent {


    private Long member_id;
    private UserAdmin userAdmin;


    public UserAdminChangeEvent(Long member_id, UserAdmin userAdmin) {
        this.member_id = member_id;
        this.userAdmin = userAdmin;
    }
}
