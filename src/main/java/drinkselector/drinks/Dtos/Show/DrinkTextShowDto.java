package drinkselector.drinks.Dtos.Show;

import drinkselector.drinks.Etcs.Enums.DrinkType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrinkTextShowDto {


    private Long drink_id;
    private String drink_name;

    private String drink_description;

    private DrinkType drinkType;



}
