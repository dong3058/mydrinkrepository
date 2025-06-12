package drinkselector.drinks.Event.Events.Update;

import drinkselector.drinks.Entity.Drinks;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



public record DrinkUpdateEvent(Long drink_id,String user_description) {

}
