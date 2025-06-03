package drinkselector.drinks.Etcs.RedisUtill;

import drinkselector.drinks.Etcs.Enums.RedisOpEnum;
import lombok.Data;

@Data
public class RedisOperationDto {


    private RedisOpEnum redisOpEnum;
    private String key;
    private String hash_key;
    private String string_value;

    public RedisOperationDto(RedisOpEnum redisOpEnum, String key, String hash_key, String string_value) {
        this.redisOpEnum = redisOpEnum;
        this.key = key;
        this.hash_key = hash_key;
        this.string_value = string_value;
    }

    public RedisOperationDto(RedisOpEnum redisOpEnum, String key, String string_value) {
        this.redisOpEnum = redisOpEnum;
        this.key = key;
        this.string_value = string_value;
    }





    public RedisOperationDto(RedisOpEnum redisOpEnum, String key) {
        this.redisOpEnum = redisOpEnum;
        this.key = key;
    }






}
