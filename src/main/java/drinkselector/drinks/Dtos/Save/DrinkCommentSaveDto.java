package drinkselector.drinks.Dtos.Save;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinkCommentSaveDto {

    private Long drink_id;
    private String comment_description;



}
