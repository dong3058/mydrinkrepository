package drinkselector.drinks.Entity;


import drinkselector.drinks.Etcs.DateSetting;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Data
@Entity
public class UserFind extends DateSetting {



    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long userfind_id;

    private Long user_id;

    private Long drink_id;

    private boolean sign_delete;

    public UserFind(Long user_id, Long drink_id) {
        this.user_id = user_id;
        this.drink_id = drink_id;
        this.sign_delete = false;
    }
}
