package drinkselector.drinks.Dtos.Show;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record DrinkSearchShowDto(Long drink_id,String drink_name,String thumb_path) {


    public DrinkSearchShowDto(Long drink_id,String drink_name){

        this(drink_id,drink_name,null);


    }

}