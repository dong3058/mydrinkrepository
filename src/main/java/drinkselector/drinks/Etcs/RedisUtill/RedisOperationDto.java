package drinkselector.drinks.Etcs.RedisUtill;

import drinkselector.drinks.Etcs.Enums.RedisOpEnum;
import lombok.Data;

//@Data
public record RedisOperationDto(RedisOpEnum redisOpEnum,String key,String hash_key,String string_value) {


    /*private RedisOpEnum redisOpEnum;
    private String key;
    private String hash_key;
    private String string_value;*/


    public RedisOperationDto(RedisOpEnum redisOpEnum, String key, String string_value) {
       this(redisOpEnum,key,null,string_value);
    }


    public RedisOperationDto(RedisOpEnum redisOpEnum, String key) {
        this(redisOpEnum,key,null,null);
    }






}
