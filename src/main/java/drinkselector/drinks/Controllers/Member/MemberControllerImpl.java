package drinkselector.drinks.Controllers.Member;


import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.CustomAnnotations.LoginUser;
import drinkselector.drinks.Serveices.MemberServices;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberControllerImpl implements MemberController {

    private final MemberServices memberServices;


    @PostMapping("/member/sign_in")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> Member_Sign_In(@RequestBody MemberDto memberDto,HttpServletRequest request) {

        return memberServices.Member_Assign(memberDto.member_mail(),memberDto.password(),request.getRemoteAddr());
    }


    @PostMapping("/member/exist")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> Check_Id_Exist(@RequestBody MemberDto memberDto) {
        return memberServices.Id_Exist_Check(memberDto.member_mail());
    }


    @PostMapping("/member/login")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> Member_Login(@RequestBody MemberDto memberDto, HttpServletRequest request) {
        return memberServices.Member_Login(memberDto.member_mail(),memberDto.password(),request.getRemoteAddr());
    }



    @Override
    @PostMapping("/member/change")
    public ResponseEntity<ApiResponseCreator<String>> Member_Change_Passowrd(@LoginUser Long member_id, @RequestBody MemberDto memberDto) {
        log.info("member_id:{}",member_id);
        return memberServices.Update_Member_Password(member_id,memberDto.password());
    }

    @PostMapping("/member/logout")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> Member_Logout() {

         return memberServices.Member_LogOut();
    }






}
