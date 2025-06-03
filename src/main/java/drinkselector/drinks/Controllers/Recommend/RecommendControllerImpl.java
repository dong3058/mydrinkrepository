package drinkselector.drinks.Controllers.Recommend;


import drinkselector.drinks.Dtos.DrinkDto;
import drinkselector.drinks.Dtos.Show.DrinkShowDto;
import drinkselector.drinks.Dtos.UserRecommendInput;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Recommend.RecommendDrink;
import drinkselector.drinks.Serveices.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendControllerImpl implements RecommendController{

    private final RecommendService recommendService;

    @PostMapping("/recommend/get/list")
    @Override
    public ResponseEntity<ApiResponseCreator<List<DrinkShowDto>>> Get_Recommend_Drink(@RequestBody  UserRecommendInput userRecommendInput) {

        return recommendService.get_recommend_drink(userRecommendInput);
    }
}
