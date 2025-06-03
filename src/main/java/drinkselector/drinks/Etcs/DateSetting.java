package drinkselector.drinks.Etcs;


import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class DateSetting {




    //@Column(nullable = false)
    @Column(nullable = true)
    public LocalDateTime sign_in_date;


    @Column(nullable = true)
    public LocalDateTime delete_date;


    @Column(nullable = true)
    public LocalDateTime update_date;




}
