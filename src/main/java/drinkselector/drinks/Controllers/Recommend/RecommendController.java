package drinkselector.drinks.Controllers.Recommend;


import drinkselector.drinks.Dtos.DrinkDto;
import drinkselector.drinks.Dtos.Show.DrinkShowDto;
import drinkselector.drinks.Dtos.UserRecommendInput;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Recommend.RecommendDrink;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name="추천 시스템에서 쓰는 컨트롤러 입니다")
public interface RecommendController {



    @Operation(summary = "음료 추천 api",description = "유저가 선택한 값을 기반으로 유사한 음료 최대 5개를 보내준다.")
    ResponseEntity<ApiResponseCreator<List<DrinkShowDto>>> Get_Recommend_Drink(UserRecommendInput userRecommendInput);
}
