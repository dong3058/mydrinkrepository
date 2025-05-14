package drinkselector.drinks.Dtos.Show;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecentSearchShowDto {


    private Long drink_id;

    private String drink_name;

    private String thumb_path;

}
