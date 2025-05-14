package drinkselector.drinks.Entity;


import drinkselector.drinks.Etcs.DateSetting;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class DrinkDescription  {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long drink_description_id;


    private String drink_description;


    @ManyToOne(fetch =FetchType.LAZY)
    private Member member;


    @OneToOne(fetch = FetchType.LAZY)
    private Drinks drink;

    public DrinkDescription(String drink_description,Drinks drink,Member member) {
        this.drink_description = drink_description;
        this.drink = drink;
        this.member=member;
    }




}
