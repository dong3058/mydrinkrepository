package drinkselector.drinks.Event.Events.UserAdminEvent;


import drinkselector.drinks.Etcs.Enums.UserAdmin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAdminEvent {

    private Long member_id;
    private String member_name;
    private UserAdmin userAdmin;
    private String redis_work_id;


    public UserAdminEvent(Long member_id,String redis_work_id) {
        this.member_id = member_id;
        this.redis_work_id=redis_work_id;
    }

    public UserAdminEvent(Long member_id, UserAdmin userAdmin,String redis_work_id) {
        this.member_id = member_id;
        this.userAdmin = userAdmin;
        this.redis_work_id=redis_work_id;
    }

    public UserAdminEvent(String member_name,String redis_work_id) {
        this.member_name = member_name;
        this.redis_work_id=redis_work_id;
    }
}
