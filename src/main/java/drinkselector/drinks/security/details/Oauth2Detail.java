package drinkselector.drinks.security.details;

import drinkselector.drinks.Etcs.Enums.Oauth2Enum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class Oauth2Detail implements UserDetails {


    private final Oauth2Enum oauth2Enum;

    private final String redirect_url;

    public Oauth2Enum getOauth2Enum() {
        return oauth2Enum;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername()  {return redirect_url;
    }
}
