package drinkselector.drinks.Repository;

import drinkselector.drinks.Entity.DrinkDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DrinkDescriptionRepository extends JpaRepository<DrinkDescription,Long> {


    @Modifying
    @Query("update DrinkDescription d set d.drink_description=:description where d.drink.drink_id=:drink_id")
    void update_drink_description(@Param(value="description") String description,@Param(value ="drink_id") Long drink_id);



}
