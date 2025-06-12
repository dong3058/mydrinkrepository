package drinkselector.drinks.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@AllArgsConstructor
@NoArgsConstructor*/
public record MailAuthCodeDto(String mail,String auth_code) {

    /*private String mail;
    private String auth_code;*/

}
