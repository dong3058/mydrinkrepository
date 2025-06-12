package drinkselector.drinks.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Data
@AllArgsConstructor
@NoArgsConstructor*/
public record EmailAuthDto(String user_mail,String auth_code) {
    /*private String user_mail;
    private String auth_code;*/
}
