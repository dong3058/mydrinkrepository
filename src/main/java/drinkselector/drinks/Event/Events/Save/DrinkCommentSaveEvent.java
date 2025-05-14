package drinkselector.drinks.Event.Events.Save;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrinkCommentSaveEvent {

    private Long member_id;
    private Long drink_id;
    private String description;

}
