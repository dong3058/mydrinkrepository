package drinkselector.drinks.security;

import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.security.details.Oauth2Detail;

public interface Oauth2Solution {

    String Get_Access_Code(String query_str);

    String Get_Access_Token(String Access_Code,Oauth2Detail oauth2Detail);


    Member Get_User_Info(String Access_Code);



}
