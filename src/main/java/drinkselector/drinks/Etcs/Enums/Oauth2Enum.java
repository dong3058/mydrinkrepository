package drinkselector.drinks.Etcs.Enums;

import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;

public enum Oauth2Enum {


    Kakao("http://localhost:8080/kakao/login","z","https://kauth.kakao.com/oauth/token","https://kapi.kakao.com/v1/oidc/userinfo","https://kapi.kakao.com/v1/user/logout");
    Oauth2Enum(String redirect_url, String client_id, String request_access_token_url, String request_user_info_url,String request_logout_url) {
        this.redirect_url = redirect_url;
        this.client_id = client_id;
        this.request_access_token_url = request_access_token_url;
        this.request_user_info_url = request_user_info_url;
        this.request_logout_url=request_logout_url;
    }

    private String  redirect_url;

    private String client_id;

    private String request_access_token_url;

    private String request_user_info_url;

    private String request_logout_url;

    public String getRedirect_url() {
        return redirect_url;
    }

    public String getClient_id() {
        return client_id;
    }


    public String getRequest_access_token_url() {
        return request_access_token_url;
    }


    public String getRequest_user_info_url() {
        return request_user_info_url;
    }


}
