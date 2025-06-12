package drinkselector.drinks.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*@Data
@NoArgsConstructor*/
public record KakaoUserDataDto(String sub,String email) {
    /*private String sub;

    private String email;*/
}
