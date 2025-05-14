package drinkselector.drinks.Dtos.Save;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DrinkCommentSaveDto {

    private Long drink_id;
    private String comment_description;



}
