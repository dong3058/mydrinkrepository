package drinkselector.drinks.Dtos.Show;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@AllArgsConstructor
@NoArgsConstructor*/
public record RecentSearchShowDto(Long drink_id,String drink_name,String thumb_path) {


    /*private Long drink_id;

    private String drink_name;

    private String thumb_path;*/

}
