package drinkselector.drinks.Repository;

import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {



    @Query("select m from Member m where m.member_mail=:email")
    Optional<Member> Check_User_Exist(@Param("email") String email);


    @Query("update Member m set m.userAdmin=:admin where m.member_id=:member_id")
    Optional<Member> Change_Member_Admin(@Param(value = "member_id") Long member_id,@Param(value="admin") UserAdmin admin);

}
