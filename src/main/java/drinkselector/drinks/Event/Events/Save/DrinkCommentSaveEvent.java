package drinkselector.drinks.Event.Events.Save;

import lombok.AllArgsConstructor;
import lombok.Data;


public record DrinkCommentSaveEvent(Long member_id,Long drink_id,String description) {



}
