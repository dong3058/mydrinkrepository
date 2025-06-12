package drinkselector.drinks.Dtos.Save;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record DrinkSaveDto(String drink_type,String drink_name,String description) {

}
