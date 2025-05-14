package drinkselector.drinks.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MemberDto {

    public String member_mail;
    public String password;
}
