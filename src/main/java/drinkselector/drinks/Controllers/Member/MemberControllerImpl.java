package drinkselector.drinks.Controllers.Member;


import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.CustomAnnotations.LoginUser;
import drinkselector.drinks.Serveices.MemberServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberControllerImpl implements MemberController {

    private final MemberServices memberServices;

    @Override
    public ResponseEntity<ApiResponseCreator<String>> Member_Sign_In(@RequestBody MemberDto memberDto) {
        return memberServices.Member_Assign(memberDto.getMember_mail(),memberDto.getPassword());
    }

    @Override
    public ResponseEntity<ApiResponseCreator<String>> Check_Id_Exist(@RequestBody MemberDto memberDto) {
        return memberServices.Id_Exist_Check(memberDto.getMember_mail());
    }

    @Override
    public ResponseEntity<ApiResponseCreator<String>> Member_Login(@RequestBody MemberDto memberDto) {
        return memberServices.Member_Login(memberDto.getMember_mail(),memberDto.getPassword());
    }

    @Override
    @PostMapping("/member/change")
    public ResponseEntity<ApiResponseCreator<String>> Member_Change_Passowrd(@LoginUser Long member_id, @RequestBody MemberDto memberDto) {
        return memberServices.Update_Member_Password(member_id,memberDto.getPassword());
    }

    @Override
    public ResponseEntity<ApiResponseCreator<String>> Member_Logout() {

         return memberServices.Member_LogOut();
    }






}
