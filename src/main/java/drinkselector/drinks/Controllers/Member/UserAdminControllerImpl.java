package drinkselector.drinks.Controllers.Member;


import drinkselector.drinks.Dtos.Save.DrinkCommentSaveDto;
import drinkselector.drinks.Dtos.MemberInfoDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Serveices.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserAdminControllerImpl implements UserAdminController{



    private final UserAdminService userAdminService;

    @Override
    public ResponseEntity<ApiResponseCreator<MemberInfoDto>> Show_Member_Data(String member_name) {
        return userAdminService.Get_Member_Info(member_name);
    }

    @Override
    public ResponseEntity<ApiResponseCreator<List<DrinkCommentSaveDto>>> Show_Member_Log(Long member_id) {
        return userAdminService.Get_Member_Comment(member_id);
    }

    @Override
    public ResponseEntity<ApiResponseCreator<UserAdmin>> Ban_User(MemberInfoDto memberInfoDto) {
        return userAdminService.Change_Member_Admin(memberInfoDto.getMember_id(),memberInfoDto.getUserAdmin());
    }
}
