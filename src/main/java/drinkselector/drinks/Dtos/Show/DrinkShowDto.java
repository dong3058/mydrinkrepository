package drinkselector.drinks.Dtos.Show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrinkShowDto implements Comparable<DrinkShowDto> {


    private Long drink_id;
    private String thumbnail_path;

    private String drink_name;


    @Override
    public int compareTo(DrinkShowDto o) {
        return this.drink_id.compareTo(o.drink_id);
    }
}
