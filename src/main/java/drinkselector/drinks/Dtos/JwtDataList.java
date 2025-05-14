package drinkselector.drinks.Dtos;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtDataList {


    private final Long member_id;

    private final String user_admin;
}
