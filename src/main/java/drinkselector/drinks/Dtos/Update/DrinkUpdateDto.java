package drinkselector.drinks.Dtos.Update;


import lombok.Data;

@Data
public class DrinkUpdateDto {

    private String description;



    public DrinkUpdateDto(String description){
        this.description = description;

    }
}
