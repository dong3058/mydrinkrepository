package drinkselector.drinks.Serveices;


import drinkselector.drinks.Dtos.DrinkDto;
import drinkselector.drinks.Dtos.Show.DrinkShowDto;
import drinkselector.drinks.Dtos.UserRecommendInput;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.Recommend.RecommendDrink;
import drinkselector.drinks.Etcs.Recommend.UserRecommendUtils;
import lombok.RequiredArgsConstructor;
import org.nd4j.shade.protobuf.Api;
import org.nd4j.shade.protobuf.ApiOrBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService {


    private final UserRecommendUtils userRecommendUtils;



   public ResponseEntity<ApiResponseCreator<List<DrinkShowDto>>> get_recommend_drink(UserRecommendInput userRecommendInput){

        double [] user_score=new double[5];

        user_score[0]=userRecommendInput.getSweat();
        user_score[1]=userRecommendInput.getDry();
        user_score[2]=userRecommendInput.getSharp();
        user_score[3]=userRecommendInput.getBitter();
        user_score[4]=userRecommendInput.getBody();

        return ResponseEntity.ok(ApiResponseCreator.success(userRecommendUtils.Get_User_Recommend_Drink(user_score,userRecommendInput.getDrink_type()),StateEnum.Success_Normally.getStates()));



   }
}
