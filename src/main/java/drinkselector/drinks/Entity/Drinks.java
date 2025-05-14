package drinkselector.drinks.Entity;


import drinkselector.drinks.Etcs.DateSetting;
import drinkselector.drinks.Etcs.Enums.DrinkType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Drinks extends DateSetting {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long drink_id;

    @Enumerated(EnumType.STRING)
    private DrinkType drinkType;

    @Column(nullable = false)
    private String drink_name;



    private long sweat;

    private long dry;

    private long sharp;

    private long bitter;


    private long body;


    public Drinks(DrinkType drinkType, String drink_name) {
        this.drinkType = drinkType;
        this.drink_name = drink_name;
    }


    public Drinks(DrinkType drinkType, String drink_name, long sweat, long dry, long sharp, long bitter, long body) {
        this.drinkType = drinkType;
        this.drink_name = drink_name;
        this.sweat = sweat;
        this.dry = dry;
        this.sharp = sharp;
        this.bitter = bitter;
        this.body = body;
    }
}
