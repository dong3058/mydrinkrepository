package drinkselector.drinks.Repository;

import drinkselector.drinks.Dtos.UserFindDto;
import drinkselector.drinks.Entity.UserFind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserFindRepository extends JpaRepository<UserFind,Long>{


    /*@Query("select drinkselector.drinks.Dtos.UserFindDto(u.user_id,d.drink_id,d.drink_name) from UserFind u inner join Drinks d on d.user_id=u.user_id where u.user_id=:user_id and u.sign_delete!=true order by u.sign_in_date")
    List<UserFindDto> Find_User_Choose_List(@Param("user_id")Long user_id);



    @Query("select u from UserFind u where u.user_id:=user_id and u.drink_id=:drink_id")
    Optional<UserFind> Find_Exist_List(@Param("user_id")Long user_id,@Param("drink_id") Long drink_id);*/

}
