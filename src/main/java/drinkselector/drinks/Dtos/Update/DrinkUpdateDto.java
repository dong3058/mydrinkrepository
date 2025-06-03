package drinkselector.drinks.Dtos.Update;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DrinkUpdateDto {

    private String description;



    public DrinkUpdateDto(String description){
        this.description = description;

    }
}
