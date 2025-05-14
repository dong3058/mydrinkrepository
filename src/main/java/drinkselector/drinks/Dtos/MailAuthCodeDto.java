package drinkselector.drinks.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailAuthCodeDto {

    private String mail;
    private String auth_code;
}
