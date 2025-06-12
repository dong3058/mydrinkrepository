package drinkselector.drinks.Controllers.DrinkComment;


import drinkselector.drinks.Dtos.Save.DrinkCommentSaveDto;
import drinkselector.drinks.Dtos.Show.DrinkCommentShowDto;
import drinkselector.drinks.Dtos.Update.DrinkCommentUpdateDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "음료 댓글 컨트롤러")
public interface DrinkCommentController {


    @Operation(summary = "유저가 음료에 댓글 다는 기능",description = "post로 진행할건대 member식별은 헤더를 기준으로 할것")
    ResponseEntity<ApiResponseCreator<String>> Make_Drink_Comment(Long member_id, DrinkCommentSaveDto drinkCommentDto);


    @Operation(summary = "해당 되는 댓글 리스트 가져오기",description = "get 메서드로 진행할것.")
    ResponseEntity<ApiResponseCreator<List<DrinkCommentShowDto>>> Get_Comment_List(Long drink_id);


    @Operation(summary = "댓글 수정 기능")
    ResponseEntity<ApiResponseCreator<String>> update_comment(Long member_id, DrinkCommentUpdateDto drinkCommentUpdateDto);


    @Operation(summary = "댓글 삭제 기능")
    ResponseEntity<ApiResponseCreator<String>> delete_comment(Long member_id,DrinkCommentUpdateDto drinkCommentUpdateDto);

}
