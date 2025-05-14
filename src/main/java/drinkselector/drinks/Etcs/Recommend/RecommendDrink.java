package drinkselector.drinks.Etcs.Recommend;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendDrink implements Comparable {



    private Long drink_id;

    private Long cosine;

    @Override
    public int compareTo(Object o) {
        RecommendDrink recommendDrink=(RecommendDrink) o;


        return ((RecommendDrink) o).getCosine().compareTo(this.cosine);
    }
}
