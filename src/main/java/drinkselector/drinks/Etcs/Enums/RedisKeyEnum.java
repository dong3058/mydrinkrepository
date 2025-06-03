package drinkselector.drinks.Etcs.Enums;

public enum RedisKeyEnum {
     /*
    *
    * 레디스 저장된 내용 리스트
    * 1."년월요일시간"--> zset으로 스코어 보드화, 시간단위로 검색어값을 키로 몇개가 검색되었는지를 기록
    * 2.serach_by_{user_id}:이걸 키값으로 유저가 검색한 검색어를 기록 10개 가최대
    * 3.hash type  member_mail--> 로그인 횟수  email 리스트 로그인 보안 필요여부
    * 4.user_login_count : member_mail : 로그인 횟수
    * 5.user_login_ip :member_mail :ip
      *
      * drink_id->set에서 drink_id를 key로 value로는 member_id가들어감.
      *
      * usermail의 경우 이메일 인증에대해서 mail:인증코드 이렇게 저장.
      *
      * hash drink--> drink: drink_id :drink_id-drink_name,dirnk_Type의 이름  이렇게 구성.
      *
      * {user_id}_list--> 유저 아이디를 key로 drinkid라는 hashkey가존재. 그안에 음료이름이 들어잇음.
      *
      *
      * drink_feature_table : 각 음료들의 특성값을 저장해둔 array가 value로 들어가있다.
      *
      *
      * user_admin: member_id :user_roll
    * */

    User_Login_Count("user_login_count"),User_Login_Ip("user_login_ip"),Member_Mail("member_mail"),Empty_User_mail_For_Value_Op("")
    ,Empty_Drink_Id_For_Set_Op(""),User_Admin("user_admin");


    private String key;

    RedisKeyEnum(String key) {
        this.key=key;
    }


    public String getKey() {
        return key;
    }


}
