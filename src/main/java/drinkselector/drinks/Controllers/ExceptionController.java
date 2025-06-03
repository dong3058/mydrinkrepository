package drinkselector.drinks.Controllers;


import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.Exceptions.AdminError;
import drinkselector.drinks.Etcs.Exceptions.ExistAccountError;
import drinkselector.drinks.Etcs.Exceptions.ReLoginError;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionController {

    private final CookieUtils cookieUtils;
    private final JwtCreators jwtProviders;

    @ExceptionHandler(ReLoginError.class)
    public ResponseEntity<ApiResponseCreator<String>> ReLogin_Need(Exception e){

        HttpHeaders headers_for_logout=cookieUtils.Make_Header_With_Cookie_Logout();


        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Need_Login.getStates()),headers_for_logout, HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(AdminError.class)
    public ResponseEntity<ApiResponseCreator<String>> Admin_Now_Allowd(Exception e){

        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Not_Allowed_Member.getStates()),HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(ExistAccountError.class)
    public ResponseEntity<ApiResponseCreator<String>> Exist_Account_Errors(Exception e){

        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Exist_Id.getStates()),HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseCreator<String>> EtcErrors(Exception e){
        log.info("error:{}",e);
        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Normally.getStates()),HttpStatus.BAD_REQUEST);


    }
}
