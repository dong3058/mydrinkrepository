package drinkselector.drinks.Controllers.Member;


import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Dtos.Save.DrinkCommentSaveDto;
import drinkselector.drinks.Dtos.MemberInfoDto;
import drinkselector.drinks.Dtos.Show.DrinkCommentShowDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.CustomAnnotations.LoginUser;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Serveices.UserAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserAdminControllerImpl implements UserAdminController{



    private final UserAdminService userAdminService;



    @PostMapping("/admin/show/member_data")
    @Override
    public ResponseEntity<ApiResponseCreator<MemberInfoDto>> Show_Member_Data(@RequestBody  MemberDto memberDto) {
        return userAdminService.Get_Member_Info(memberDto.member_mail());
    }

    @GetMapping("/admin/log/{member_id}")
    @Override
    public ResponseEntity<ApiResponseCreator<List<DrinkCommentShowDto>>> Show_Member_Log(@PathVariable(name = "member_id") Long member_id) {
        return userAdminService.Get_Member_Comment(member_id);
    }


    @PostMapping("/admin/change")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> Change_User(@LoginUser Long member_id,@RequestBody MemberInfoDto memberInfoDto) {



        return userAdminService.Change_Member_Admin(member_id,memberInfoDto.userAdmin());
    }
}
