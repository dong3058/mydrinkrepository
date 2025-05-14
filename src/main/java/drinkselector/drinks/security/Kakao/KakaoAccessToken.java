package drinkselector.drinks.security.Kakao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoAccessToken {

    private String access_token;
   /* private String token_type;
    private String refresh_token;
    private String id_token;
    private Integer expires_in;
    private String scope;
    private String refresh_token_expires_in;


    public KakaoAccessToken(String access_token, String token_type, String refresh_token, String id_token, Integer expires_in, String scope, String refresh_token_expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
        this.id_token = id_token;
        this.expires_in = expires_in;
        this.scope = scope;
        this.refresh_token_expires_in = refresh_token_expires_in;
    }*/

    public KakaoAccessToken(String access_token) {
        this.access_token = access_token;
    }
}
