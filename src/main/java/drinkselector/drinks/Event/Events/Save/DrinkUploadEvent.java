package drinkselector.drinks.Event.Events.Save;


import drinkselector.drinks.Entity.Drinks;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public record DrinkUploadEvent(Long member_id,Drinks drink,String user_description) {
}
