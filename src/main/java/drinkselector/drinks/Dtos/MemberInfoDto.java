package drinkselector.drinks.Dtos;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {

    private Long member_id;
    private String member_name;
    private UserAdmin userAdmin;



    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sign_in_date;


}
