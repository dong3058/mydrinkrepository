package drinkselector.drinks.Etcs.Jwts;


import com.fasterxml.jackson.databind.ObjectMapper;
import drinkselector.drinks.Dtos.JwtDataList;
import drinkselector.drinks.Etcs.Enums.RedisOpEnum;
import drinkselector.drinks.Etcs.Enums.UserAdmin;
import drinkselector.drinks.Etcs.Exceptions.ReLoginError;
import drinkselector.drinks.Etcs.RedisUtill.RedisOperationDto;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtCreators {


    @Value("${jwt.signkey}")
    private String sign_key;


    @Value("${jwt.expiration}")
    private  Long expiration;


    private final ObjectMapper objectMapper=new ObjectMapper();


    private final RedisUtills redisUtills;
    private static SecretKeySpec encrypt_key;


    public Jwt gen_token(long member_id, UserAdmin userAdmin){
        Long now=System.currentTimeMillis();



        String encrypt_member_id=Make_Encrypt_Member_id(member_id);
        String accesstoken=Jwts.builder()
                .claim("member_id",encrypt_member_id)

                .setExpiration(new Date(now+expiration))
                .setIssuedAt(new Date(now))
                .signWith(SignatureAlgorithm.HS256,sign_key)
                .compact();

        return Jwt.builder()
                .access_token(accesstoken)
                .build();



    }


    public byte [] Make_Encrypt_Salt(){

        SecureRandom secureRandom=new SecureRandom();
        byte [] salt =new byte[16];

        secureRandom.nextBytes(salt);



        return salt;


    }


    public void Make_Encrypt_Key(String sign_key,byte [] salt){



        try {
            int count = 1000;
            int keyLength = 256;

            PBEKeySpec pbeKeySpec = new PBEKeySpec(sign_key.toCharArray(), salt, count, keyLength);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] key = factory.generateSecret(pbeKeySpec).getEncoded();

            encrypt_key=new SecretKeySpec(key, "AES");


        }
        catch (Exception e){


            throw new RuntimeException();
        }

    }


    public String Make_Encrypt_Data(String data){


        try {
            Cipher cipher = Cipher.getInstance("AES");


            cipher.init(Cipher.ENCRYPT_MODE, encrypt_key);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);

        }

        catch (Exception e){
            throw new RuntimeException();
        }






    }
    public String Make_Encrypt_Member_id(long Member_id){


        try {
            Cipher cipher = Cipher.getInstance("AES");


            cipher.init(Cipher.ENCRYPT_MODE, encrypt_key);
            byte[] encryptedData = cipher.doFinal(String.valueOf(Member_id).getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);

        }

        catch (Exception e){
            throw new RuntimeException();
        }






    }


    public long Get_Member_Id_From_Jwt(String token){

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, encrypt_key);



            String[] token_arr=token.split("\\.");

            String payload=token_arr[1];
            String decode_payload=new String(Base64.getDecoder().decode(payload));

            Map<String, String> map =objectMapper.readValue(decode_payload,Map.class);

            byte[] decryptedData = cipher.doFinal(map.get("member_id").getBytes());
            return Long.parseLong(decryptedData.toString());
        }

        catch (Exception e){

            throw new RuntimeException();
        }

    }


    public JwtDataList Get_Data_From_Token(String token){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, encrypt_key);

            String[] tokenArr = token.split("\\.");
            String payload = tokenArr[1];

            String decodedPayload = new String(Base64.getDecoder().decode(payload), StandardCharsets.UTF_8);
            Map<String, String> map = objectMapper.readValue(decodedPayload, Map.class);

            log.info("map:{}",map);
            //byte[] roleBytes = Base64.getDecoder().decode(map.get("role"));
            byte[] memberIdBytes = Base64.getDecoder().decode(map.get("member_id"));

            //String role = new String(cipher.doFinal(roleBytes), StandardCharsets.UTF_8);



            String memberId = new String(cipher.doFinal(memberIdBytes), StandardCharsets.UTF_8);
            log.info("meber_id:{}",memberId);
            Optional<String> user_admin=redisUtills.RedisHashGetOperation("user_admin",memberId);

            return new JwtDataList(Long.parseLong(memberId),user_admin.get());


        }
        catch (Exception e){

            throw new RuntimeException();
        }



    }




    public boolean Valid_Jwt(String token)throws MalformedJwtException, ExpiredJwtException, UnsupportedJwtException,IllegalArgumentException{

        try {
            Jwts.parserBuilder()
                    .setSigningKey(sign_key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {

            throw new ReLoginError();
        }

    }


    @PostConstruct
    public void Init_Encrypt_Key(){


        byte [] salt=Make_Encrypt_Salt();

        Make_Encrypt_Key(sign_key,salt);


    }

}
