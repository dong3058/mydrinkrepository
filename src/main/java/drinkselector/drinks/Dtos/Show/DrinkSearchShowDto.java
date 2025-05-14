package drinkselector.drinks.Dtos.Show;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinkSearchShowDto {


    private Long drink_id;

    private String drink_name;

    private String thumb_path;

    public DrinkSearchShowDto(Long drink_id, String drink_name) {
        this.drink_id = drink_id;
        this.drink_name = drink_name;
    }
}
