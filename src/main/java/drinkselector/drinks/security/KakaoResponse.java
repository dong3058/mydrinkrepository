package drinkselector.drinks.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Map;

import static org.apache.commons.lang3.math.NumberUtils.toInt;

@Slf4j
@RequiredArgsConstructor
public class KakaoResponse implements Oauth2Response{


    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return StringUtils.defaultString((String) attribute.get("id"), null);
    }

    @Override
    public String getEmail() {
        return StringUtils.defaultString((String)attribute.get("email"), null);
    }

    @Override
    public String getName() {
        return StringUtils.defaultString((String) attribute.get("name"), "");
    }

    @Override
    public String getNickname() {
        return StringUtils.defaultString((String) attribute.get("nickname"), "");
    }


}
