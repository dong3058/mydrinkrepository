package drinkselector.drinks.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record MemberDto(String member_mail,String password) {

    //record는 기본적으로 생성자, getter( 방식이 조금 다르긴하다) 가 생성된다.
    // 또한 requestbody 매핑 이라던ㄷ가 혹은 jpa 쿼리에서 사용도 가능하다
    //또한 primitive타입이 아니라면 빈값에 대해서는 nullㄱ밧이 자동으로 들어간다(enum,특정 class객체도 마찬가지)

    //또한

    /**
     *
     *   public XX(String field1, String field3) {
     *         this(field1, null, field3);
     *     }
     *와 같이 특정 필드가 비도록 해소 new xx()~~로 생성이 가능하다.
     *
     */

    //record는 불변객체라서 상속도 불가능하고 set가 없다. 애 자체가 final이라서 entitiy에는 불가
    //jpa이용시 프록시는 final 아닌 애들을 가져다쓰기떄ㅜ문.

}
