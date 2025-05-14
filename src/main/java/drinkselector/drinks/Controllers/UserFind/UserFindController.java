package drinkselector.drinks.Controllers.UserFind;


import drinkselector.drinks.Dtos.Show.DrinkShowDto;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "유저가 선호한다고 표시한 음료에대한 컨트롤러" )
public interface UserFindController {




    @Operation(summary = "유저가 선호하는 음료들의 리스트 가져오기",description = "get 메서드임.")
    ResponseEntity<ApiResponseCreator<List<DrinkShowDto>>> Get_User_Find_List(Long user_id);

    @Operation(summary = "유저가 자신의 선호 리스트에 음료를 넣는 api",description = "get 메서드로 진행한다.")
    ResponseEntity<ApiResponseCreator<String>> Add_User_Find_List(Long user_id,Long drink_id,String drink_name);


    @Operation(summary = "유저가 자신의 선호리스트를 지우기")
    ResponseEntity<ApiResponseCreator<String>> Del_User_Find_List(Long user_id,Long drink_id);
    @Operation(summary = "유저 최근 검색 목록 가져오기",description = "음 그냥 로그인후에 상ㅇ할수있게 만들었다.")
    ResponseEntity<ApiResponseCreator<List<RecentSearchShowDto>>> Member_Recent_Search_Log(Long member_id);

}
