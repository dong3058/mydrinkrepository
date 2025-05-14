package drinkselector.drinks.Controllers.Email;


import drinkselector.drinks.Dtos.MailAuthCodeDto;
import drinkselector.drinks.Dtos.MemberDto;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Serveices.EmailService;
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
    @PostMapping("/request/authcode")
    public ResponseEntity<ApiResponseCreator<String>> Sending_Identify_Number(@RequestBody MemberDto memberDto) {
        return emailService.Sending_Auth_Code(memberDto);
    }


    @Override
    public ResponseEntity<ApiResponseCreator<String>> Checking_Identify_Number(@RequestBody MailAuthCodeDto mailAuthCodeDto) {
        return emailService.Check_Auth_Code(mailAuthCodeDto);
    }
}
