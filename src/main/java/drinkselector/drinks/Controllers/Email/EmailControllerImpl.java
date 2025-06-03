package drinkselector.drinks.Controllers.Email;


import drinkselector.drinks.Dtos.MailAuthCodeDto;
import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Serveices.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailControllerImpl implements EmailController{


    private final EmailService emailService;
    @Override
    @PostMapping("/request/auth_code")
    public ResponseEntity<ApiResponseCreator<String>> Sending_Identify_Number(@RequestBody MemberDto memberDto) {
        return emailService.Sending_Auth_Code(memberDto);
    }




    @PostMapping("/request/check_code")
    @Override
    public ResponseEntity<ApiResponseCreator<String>> Checking_Identify_Number(@RequestBody MailAuthCodeDto mailAuthCodeDto, HttpServletRequest request) {
        String ip=request.getRemoteAddr();

        return emailService.Check_Auth_Code(mailAuthCodeDto,ip);
    }
}
