package drinkselector.drinks.Dtos.Show;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrinkShowDto {


    private Long drink_id;
    private String thumb_nail_id;

    private String drink_name;
}
