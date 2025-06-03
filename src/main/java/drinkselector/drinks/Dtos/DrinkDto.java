package drinkselector.drinks.Dtos;


import drinkselector.drinks.Etcs.Enums.DrinkType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinkDto {


    private Long drink_id;

    private String drink_name;

    private String drink_description;

    private DrinkType drink_type;

    private Long like_number;

    public DrinkDto(Long drink_id, String drink_name, String drink_description, DrinkType drink_type) {
        this.drink_id = drink_id;
        this.drink_name = drink_name;
        this.drink_description = drink_description;
        this.drink_type = drink_type;

    }
}
