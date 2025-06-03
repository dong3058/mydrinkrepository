package drinkselector.drinks.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class KakaoUserDataDto {
    private String sub;

    private String email;
}
