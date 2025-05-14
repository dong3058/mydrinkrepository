package drinkselector.drinks.Repository;

import drinkselector.drinks.Entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,String> {



    @Query("select i from Image i join fetch Drinks d where d.drink_id=:drink_id")
    List<Image> Get_Image_In_Description(@Param("drink_id") Long drink_id);
}
