package drinkselector.drinks.Event.Events.UserAdminEvent;

import drinkselector.drinks.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class UserAdminGetInfoEvent {


    private String member_name;

    private Member member;

    public UserAdminGetInfoEvent(String member_name) {
        this.member_name = member_name;

    }
}
