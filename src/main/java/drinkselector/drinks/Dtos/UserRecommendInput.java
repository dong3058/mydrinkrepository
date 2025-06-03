package drinkselector.drinks.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecommendInput {

    private String drink_type;


    private long sweat;

    private long dry;

    private long sharp;

    private long bitter;


    private long body;
}
