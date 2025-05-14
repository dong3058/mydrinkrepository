package drinkselector.drinks.Dtos.Show;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data

@AllArgsConstructor
public class DrinkCommentShowDto {
    private Long comment_id;
    private Long member_name;
    private String description;
    private LocalDateTime write_time;


}
