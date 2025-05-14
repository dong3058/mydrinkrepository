package drinkselector.drinks.Entity;


import drinkselector.drinks.Etcs.DateSetting;
import drinkselector.drinks.Etcs.Enums.MemberPlatForm;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Member extends DateSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long member_id;


    @Column(name = "member_mail")
    public String member_mail;


    @Column(name="password")
    public String password;


    @Enumerated(EnumType.STRING)
    public UserAdmin userAdmin;


    @Enumerated(EnumType.STRING)
    public MemberPlatForm memberPlatForm;


    public Member(String member_mail, String password, UserAdmin userAdmin,MemberPlatForm memberPlatForm) {
        this.member_mail = member_mail;
        this.password = password;
        this.userAdmin = userAdmin;
        this.memberPlatForm=memberPlatForm;
    }
}
