package drinkselector.drinks.Repository;

import drinkselector.drinks.Dtos.DrinkDto;
import drinkselector.drinks.Dtos.Show.DrinkSearchShowDto;
import drinkselector.drinks.Entity.Drinks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DrinkRepository extends JpaRepository<Drinks,Long> {



    @Query("select drinkselector.drinks.Dtos.DrinkDto(d.drink_id,d.drink_name,desc.drink_description,d.drink_type) from Drinks d join fetch DrinkDescription desc on desc.drink_id=:drink_id")
    Optional<DrinkDto> Get_Drink_Info(@Param("drink_id") Long drink_id);
    @Query("select new drinkselector.drinks.Dtos.Show.DrinkSearchDto(d.drink_id,d.drink_name) from Drinks d where d.drink_name like %:name% ")
    List<DrinkSearchShowDto> Get_Name_Likes_Drink(Pageable pageable, @Param("name") String name);


    @Query("select new drinkselector.drinks.Dtos.Show.DrinkSearchShowDto(d.drink_id,d.drink_name,t.thumb_path) from Drinks d join ThumbNail t on t.drink_id=d.drink_id where d.drinkType=:name1")
    Page<DrinkSearchShowDto> Get_Drink_By_Category(Pageable pageable, @Param("name1") String name1);






}
