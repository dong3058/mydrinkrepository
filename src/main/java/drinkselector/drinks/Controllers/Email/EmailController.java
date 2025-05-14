package drinkselector.drinks.Controllers.Email;


import drinkselector.drinks.Dtos.MailAuthCodeDto;
import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name="이메일 인증 시스템 관련")
public interface EmailController {



    @Operation(summary = "이메일 인증 번호 발송 api",description = "가입을 진행할려고하는 emaill로 메일전송")
    ResponseEntity<ApiResponseCreator<String>> Sending_Identify_Number(MemberDto memberDto);


    @Operation(summary = "이메일 인증 번호 확인 api",description = "가입을 진행할려고하는 메일로 전송한 번호체크")
    ResponseEntity<ApiResponseCreator<String>> Checking_Identify_Number(MailAuthCodeDto mailAuthCodeDto);


}
