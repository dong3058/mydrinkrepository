package drinkselector.drinks.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailAuthCodeDto {

    private String mail;
    private String auth_code;

}
