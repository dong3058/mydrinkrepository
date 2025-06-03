package drinkselector.drinks.Controllers.Drinks;


import drinkselector.drinks.Dtos.*;
import drinkselector.drinks.Dtos.Save.DrinkSaveDto;
import drinkselector.drinks.Dtos.Show.DrinkSearchShowDto;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Dtos.Update.DrinkUpdateDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.DrinkType;
import drinkselector.drinks.Etcs.PagingEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name="사용자가 작성하는 주류에 대한 api입니다.")
public interface DrinksController {




    @Operation(summary = "해당되는 주류의 문서찾기")
    ResponseEntity<ApiResponseCreator<DrinkDto>> Search_Drink_Doc(@Parameter(name = "드링크 id만있어도 되긴하는대 이름까찌 그냥 같이주자") Long drink_id
    ,String drink_name,Long member_id);



    @Operation(summary = "주류 문서 업데이트")
    ResponseEntity<ApiResponseCreator<String>> Update_Drink_Info(@Parameter(name="업데이트할 본문이 담긴 객체") DrinkUpdateDto D, @Parameter(name="업데이트랄 id")Long drink_id);


    @Operation(summary = "유저가 작성한 주류에 대한 내용을 저장하는 api")
    ResponseEntity<ApiResponseCreator<String>> Save_Drink_Info(DrinkSaveDto userMadeDescriptionDto, Long member_id);

    @Operation(summary = "유저가 특정 주류 검색시 단어를 통해서 해당되는 검색어 식별",description = "get메서드로 진행할것.")
    ResponseEntity<ApiResponseCreator<List<DrinkSearchShowDto>>> Get_Drink_Likes_Name(String drink_name);


    @Operation(summary = "랜덤한 드링크 반환",description = "get메서드로 진행할것.")
    ResponseEntity<ApiResponseCreator<DrinkDto>> Get_Random_Drink();

    @Operation(summary = "실시간으로 검색량 제일많은거 가져오기",description = "get메서드로 진행할것.")
    ResponseEntity<ApiResponseCreator<List<RecentSearchShowDto>>> Get_Hot_Issue();



    @Operation(summary = "카테고리별로 가져오기",description = "get메서드로 진행할것.")
    ResponseEntity<ApiResponseCreator<List<DrinkSearchShowDto>>> Get_Drinks_By_Category(DrinkType category);






}
