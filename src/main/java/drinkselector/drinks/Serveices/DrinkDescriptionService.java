package drinkselector.drinks.Serveices;


import drinkselector.drinks.Entity.DrinkDescription;
import drinkselector.drinks.Entity.Drinks;
import drinkselector.drinks.Etcs.TxtFilter;
import drinkselector.drinks.Repository.DrinkDescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkDescriptionService {


    DrinkDescriptionRepository drinkDescriptionRepository;

    public String Filtering_Text(String description){



        return TxtFilter.file_txt_filter(description);


    }


    public void Update_Drink_Description(Long drink_id,String drink_description){

        drinkDescriptionRepository.update_drink_description(drink_description,drink_id);

    }

    public void Save_Drink_Description(DrinkDescription drinkDescription){

     drinkDescriptionRepository.save(drinkDescription);
    }
}
