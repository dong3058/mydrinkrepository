package drinkselector.drinks.Controllers.Member;


import drinkselector.drinks.Dtos.Save.DrinkCommentSaveDto;
import drinkselector.drinks.Dtos.MemberInfoDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name="유저 관리 api",description = "유저 관리를 위한 관리자용 api")
public interface UserAdminController {




    @Operation(summary = "유저 정보 보여주기",description = "get메서드로 진행")
    ResponseEntity<ApiResponseCreator<MemberInfoDto>> Show_Member_Data(String member_name);

    @Operation(summary = "유저 활동로그 즉 해당 유저가 달아놓은 댓글 목록 혹은 글 보여주기.",description = "get메서드로 진행")
    ResponseEntity<ApiResponseCreator<List<DrinkCommentSaveDto>>> Show_Member_Log(Long member_id);

    @Operation(summary = "유저 밴 하는 api",description = "post 메서드로 진행")
    ResponseEntity<ApiResponseCreator<UserAdmin>> Ban_User(MemberInfoDto memberInfoDto);




}
