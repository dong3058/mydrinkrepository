package drinkselector.drinks.Dtos;


import drinkselector.drinks.Etcs.Enums.UserAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MemberInfoDto {

    private Long member_id;
    private String member_name;
    private UserAdmin userAdmin;

    private LocalDateTime sign_in_date;


}
