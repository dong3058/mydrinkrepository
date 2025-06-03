package drinkselector.drinks.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ThumbNail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thumb_id;

    private Long drink_id;

    private String image_path;

    private String thumb_key;

    public ThumbNail(String thumb_path, String thumb_key) {
        this.image_path = thumb_path;
        this.thumb_key = thumb_key;
    }


}
