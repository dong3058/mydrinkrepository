package drinkselector.drinks.Event.Events.Update;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrinkCommentUpdateEvent {

    private Long member_id;
    private String member_mail;
    private Long comment_id;
    private String description;
}
