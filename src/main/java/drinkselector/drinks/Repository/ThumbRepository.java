package drinkselector.drinks.Repository;

import drinkselector.drinks.Entity.ThumbNail;
import org.nd4j.linalg.api.ops.Op;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ThumbRepository extends JpaRepository<ThumbNail,Long> {




    @Query("select t from ThumbNail t where t.drink_id=:drink_id")
    Optional<ThumbNail> find_by_drink_id(@Param("drink_id")Long drink_id);
}
