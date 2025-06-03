package drinkselector.drinks.Controllers.UserFind;


import drinkselector.drinks.Dtos.Show.DrinkShowDto;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;

import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.CustomAnnotations.LoginUser;
import drinkselector.drinks.Serveices.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserFindControllerImpl implements UserFindController{

    private final UserFindService userFindService;
    @Override
    @PostMapping("/user_find/get_list")
    public ResponseEntity<ApiResponseCreator<List<DrinkShowDto>>> Get_User_Find_List(@LoginUser Long user_id) {
        return userFindService.Get_User_Find_List(user_id);
    }


    @Override
    @PostMapping("/user_find/add_list/{drink_id}/{drink_name}")
    public ResponseEntity<ApiResponseCreator<String>> Add_User_Find_List(@LoginUser  Long user_id, @PathVariable("drink_id") Long drink_id,@PathVariable("drink_name") String drink_name) {
        return userFindService.Add_User_List(user_id,drink_id,drink_name);
    }


    @Override
    @PostMapping("/user_find/del_list/{drink_id}")
    public ResponseEntity<ApiResponseCreator<String>> Del_User_Find_List(@LoginUser Long user_id,@PathVariable(name="drink_id") Long drink_id) {
        return userFindService.Del_User_List(user_id,drink_id);
    }

    @Override
    @PostMapping("/user_find/log_list")
    public ResponseEntity<ApiResponseCreator<List<RecentSearchShowDto>>> Member_Recent_Search_Log(@LoginUser Long member_id) {
        return userFindService.get_recent_search_log(member_id);
    }
}
