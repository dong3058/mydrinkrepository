package drinkselector.drinks.Controllers.DrinkComment;

import drinkselector.drinks.Dtos.Save.DrinkCommentSaveDto;
import drinkselector.drinks.Dtos.Show.DrinkCommentShowDto;
import drinkselector.drinks.Dtos.Update.DrinkCommentUpdateDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.CustomAnnotations.LoginUser;
import drinkselector.drinks.Serveices.DrinkCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class DrinkCommentControllerImpl implements DrinkCommentController {


    private final DrinkCommentService drinkCommentService;


    @Override
    @PostMapping("/comment/save/drink_comment")
    public ResponseEntity<ApiResponseCreator<String>> Make_Drink_Comment(@LoginUser Long member_id, @RequestBody DrinkCommentSaveDto drinkCommentDto) {

        return drinkCommentService.Save_DrinkComment(member_id,drinkCommentDto);
    }


    @Override
    @GetMapping("/comment/get/{drink_id}")
    public ResponseEntity<ApiResponseCreator<List<DrinkCommentShowDto>>> Get_Comment_List(@PathVariable(name = "drink_id")Long drink_id) {
        return drinkCommentService.Get_Drink_List(drink_id);


    }

    @PostMapping("/comment/update/drink_comment")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> update_comment(@LoginUser  Long member_id,@RequestBody DrinkCommentUpdateDto drinkCommentUpdateDto) {
        return drinkCommentService.Update_Drink_Comment(member_id,drinkCommentUpdateDto);
    }


    @PostMapping("/comment/delete")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> delete_comment(@LoginUser Long member_id,@RequestBody DrinkCommentUpdateDto drinkCommentUpdateDto) {
        return null;
    }
}
