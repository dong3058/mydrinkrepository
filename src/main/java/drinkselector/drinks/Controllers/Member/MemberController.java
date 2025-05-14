package drinkselector.drinks.Controllers.Member;


import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


@Tag(name = "멤버 컨트롤러",description = "회원 관련된 api 목록입니다.")
public interface MemberController {




    @Operation(summary = "일반화원 가입용 api",description = "일반 적인 가입 루트에따른 회원가입 api입니다")
    ResponseEntity<ApiResponseCreator<String>> Member_Sign_In(@Parameter(name="회원 가입시 전달해야알 memberdto")MemberDto memberDto);



    @Operation(summary ="해당 이메일 회원  존재 여부 체크 api",description="이메일만 들어가도 상관없습니다.")
    ResponseEntity<ApiResponseCreator<String>> Check_Id_Exist(@Parameter(name="회원 가입시 사용할 이메일이 들은 dto")MemberDto memberDto);


    @Operation(summary = "일반적인 로그인 api",description = "일반적인 로그인 루트 api입니다")
    ResponseEntity<ApiResponseCreator<String>> Member_Login(@Parameter(name="로그인시 전달해야알 memberdto")MemberDto memberDto);

    @Operation(summary = "일반적인 비밀번호 교체 api",description = "일반적인 비밀번호 교체 api입니다")
    ResponseEntity<ApiResponseCreator<String>> Member_Change_Passowrd(@Parameter(name="쿠키를 첨부해서 전달 해주면된다.") Long member_id,@Parameter(name="비빌번호 교체시 보내야할 memberdto") MemberDto memberDto);

    @Operation(summary = "일반적인 로그아웃 api",description = "일반적인 로그아웃 루트 api입니다")
    ResponseEntity<ApiResponseCreator<String>> Member_Logout();





}
