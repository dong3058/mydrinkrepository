package drinkselector.drinks.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Image {
    @Id
    private String image_id;
    private String image_name;
    @ManyToOne
    private Drinks drink;

    public Image(String image_id,String image_name,Drinks drink) {
        this.image_id=image_id;
        this.image_name = "image_path"+image_id+"-"+image_name;
        this.drink=drink;
    }
}
