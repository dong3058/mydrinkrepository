package drinkselector.drinks.Controllers.Drinks;

import drinkselector.drinks.Dtos.*;
import drinkselector.drinks.Dtos.Save.DrinkSaveDto;
import drinkselector.drinks.Dtos.Show.DrinkSearchShowDto;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Dtos.Update.DrinkUpdateDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.CustomAnnotations.LoginUser;
import drinkselector.drinks.Etcs.Enums.DrinkType;
import drinkselector.drinks.Etcs.PagingEntity;
import drinkselector.drinks.Serveices.DrinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class DrinksControllerImpl implements DrinksController{



    private final DrinkService drinkService;


    @PostMapping("/drink/save")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> Save_Drink_Info(@RequestBody DrinkSaveDto userMadeDescriptionDto, @LoginUser Long member_id) {
        return drinkService.Save_Drink_Info(userMadeDescriptionDto,member_id);
    }

    @PostMapping("/drink/update/{drink_id}")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> Update_Drink_Info(@RequestBody DrinkUpdateDto D, @PathVariable("drink_id") Long drink_id) {
         return drinkService.Update_Drink_Info(D,drink_id);
    }

    @Override
    @GetMapping("/drink/search_list/{drink_name}")
    public ResponseEntity<ApiResponseCreator<List<DrinkSearchShowDto>>> Get_Drink_Likes_Name(@PathVariable("drink_name") String drink_name) {
        return drinkService.get_name_likes_drink(drink_name);
    }


    @Override
    @GetMapping("/drink/search/{drink_id}/{drink_name}")
    public ResponseEntity<ApiResponseCreator<DrinkDto>> Search_Drink_Doc(@PathVariable(name="drink_id") Long drink_id,@PathVariable(name ="drink_name") String drink_name, @LoginUser Long member_id) {
        return drinkService.Get_Drink_Info(drink_id,drink_name,member_id);
    }

    @GetMapping("/drink/search/random")
    @Override
    public ResponseEntity<ApiResponseCreator<DrinkDto>> Get_Random_Drink() {
        return drinkService.Get_Random_Drink();
    }




    @GetMapping("/drink/issuesearch")
    @Override
    public ResponseEntity<ApiResponseCreator<List<RecentSearchShowDto>>> Get_Hot_Issue() {
        return drinkService.get_hot_issue();
    }


    @GetMapping("/drink/search/category/{category}")
    @Override
    public ResponseEntity<ApiResponseCreator<List<DrinkSearchShowDto>>> Get_Drinks_By_Category(@PathVariable(name = "category") DrinkType category) {
        return drinkService.Get_Drink_By_Category(category);
    }
}
