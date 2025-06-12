package drinkselector.drinks.Dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*@Data
@AllArgsConstructor
@NoArgsConstructor*/
public record MemberInfoDto(Long member_id,String member_name,UserAdmin userAdmin,

                            @JsonSerialize(using = LocalDateTimeSerializer.class)
                            @JsonDeserialize(using = LocalDateTimeDeserializer.class)
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                            LocalDateTime sign_in_date) {

   /* private Long member_id;
    private String member_name;
    private UserAdmin userAdmin;



    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime sign_in_date;*/


}
