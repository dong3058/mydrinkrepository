package drinkselector.drinks.Dtos;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/*@Data
@RequiredArgsConstructor*/
public record JwtDataList(Long member_id,String user_admin) {


    /*private final Long member_id;

    private final String user_admin;*/

}
