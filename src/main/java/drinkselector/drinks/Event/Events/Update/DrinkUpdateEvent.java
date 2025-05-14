package drinkselector.drinks.Event.Events.Update;

import drinkselector.drinks.Entity.Drinks;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@AllArgsConstructor
public class DrinkUpdateEvent {
    private Long drink_id;
    private String user_description;
}
