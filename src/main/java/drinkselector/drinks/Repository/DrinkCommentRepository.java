package drinkselector.drinks.Repository;


import drinkselector.drinks.Dtos.Show.DrinkCommentShowDto;
import drinkselector.drinks.Entity.DrinkComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrinkCommentRepository extends JpaRepository<DrinkComment,Long> {



    @Query("select d from DrinkComment d where d.writer.member_id=:member_id")
    List<DrinkComment> Get_Drink_Comment_List_By_Member(@Param(value="member_id")Long member_id);


    @Query("select new drinkselector.dtos.DrinkCommentShowDto(d.comment_id,m.member_mail,d.comment_description,d.update_time) from DrinkComment d join fetch d.member m join fetch d.drink dk where dk.drink_id=:drink_id")
    List<DrinkCommentShowDto> Get_Drink_Comment_List(@Param("drink_id") Long drink_id);



}
