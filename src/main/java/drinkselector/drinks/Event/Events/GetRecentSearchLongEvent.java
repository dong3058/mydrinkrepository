package drinkselector.drinks.Event.Events;

import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetRecentSearchLongEvent {

    private List<RecentSearchShowDto> recentSearchDtos;


}
