package drinkselector.drinks.Event.Events;


import lombok.Data;
import lombok.NoArgsConstructor;


public record RecentSearchLogEvent(Long member_id,Long drink_id,String drink_name) {

    public RecentSearchLogEvent(Long drink_id, String drink_name) {
       this(null,drink_id,drink_name);
    }

}
