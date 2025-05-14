package drinkselector.drinks.Event.Events;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecentSearchLogEvent {

    private Long member_id;
    private Long drink_id;
    private String drink_name;

    public RecentSearchLogEvent(Long drink_id, String drink_name) {
        this.drink_id = drink_id;
        this.drink_name = drink_name;
    }

    public RecentSearchLogEvent(Long member_id, Long drink_id, String drink_name) {
        this.member_id = member_id;
        this.drink_id = drink_id;
        this.drink_name = drink_name;
    }
}
