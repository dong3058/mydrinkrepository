package drinkselector.drinks.Dtos.Update;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DrinkCommentUpdateDto {

    private Long comment_id;
    private String member_name;
    private String description;
    private Long member_id;

}
