package drinkselector.drinks.Dtos.Save;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinkSaveDto {


    private String drink_type;

    private String drink_name;

    private String description;

}
