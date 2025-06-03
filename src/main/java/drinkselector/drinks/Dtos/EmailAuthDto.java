package drinkselector.drinks.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAuthDto {
    private String user_mail;
    private String auth_code;
}
