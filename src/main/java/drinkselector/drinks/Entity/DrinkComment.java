package drinkselector.drinks.Entity;


import drinkselector.drinks.Etcs.DateSetting;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DrinkComment extends DateSetting {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long comment_id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="drinkDescription_id")
    private DrinkDescription drinkDescription_id;

    private String comment_description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id")
    private Drinks drink;






    public DrinkComment(Drinks drink, String comment_description, Member member) {
        this.drink = drink;
        this.comment_description = comment_description;
        this.member = member;
    }


}
