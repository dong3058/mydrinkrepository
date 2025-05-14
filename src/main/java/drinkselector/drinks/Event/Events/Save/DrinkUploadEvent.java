package drinkselector.drinks.Event.Events.Save;


import drinkselector.drinks.Entity.Drinks;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class DrinkUploadEvent {
    private Long member_id;
    private Drinks drink;
    private String user_description;
}
