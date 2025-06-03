package drinkselector.drinks.Serveices;


import drinkselector.drinks.Entity.Member;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Cookie.CookieUtils;
import drinkselector.drinks.Etcs.Enums.*;
import drinkselector.drinks.Etcs.Exceptions.ExistAccountError;
import drinkselector.drinks.Etcs.Jwts.Jwt;
import drinkselector.drinks.Etcs.Jwts.JwtCreators;
import drinkselector.drinks.Etcs.RedisUtill.RedisOperationDto;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import drinkselector.drinks.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServices {


    private final MemberRepository memberRepository;
    private final CookieUtils cookieUtils;
    private final JwtCreators jwtProviders;


    private final RedisUtills redisUtills;
    private final PasswordEncoder bCryptPasswordEncoder;







    public ResponseEntity<ApiResponseCreator<String>> Id_Exist_Check(String mail){
        Optional<Member> member_exist=memberRepository.Check_User_Exist(mail);

        log.info("멤버 존재여부:{}",member_exist.isPresent());
        if(member_exist.isPresent()){

            throw new ExistAccountError();
        }


        log.info("에러발생?");
        return ResponseEntity.ok(ApiResponseCreator.success("성공",StateEnum.Success_Normally.getStates()));


    }



    public ResponseEntity<ApiResponseCreator<String>> Member_Assign(String mail, String password,String ip){


        Optional<Member> member_exist=memberRepository.Check_User_Exist(mail);

        if(member_exist.isPresent()){
            throw new ExistAccountError();
        }

        String hashed_password=bCryptPasswordEncoder.encode(password);
        Member member;
        UserAdmin userAdmin;
        if(mail.equals("dong.3058@daum.net")){
            member=new Member(mail,hashed_password, UserAdmin.ROLE_Admin, MemberPlatForm.Normal);
            userAdmin=UserAdmin.ROLE_Admin;
        }
        else {
            member = new Member(mail, hashed_password, UserAdmin.ROLE_User, MemberPlatForm.Normal);

            userAdmin=UserAdmin.ROLE_User;
        }
        LocalDateTime now=LocalDateTime.now();
        member.setSign_in_date(now);
        member.setUpdate_date(now);

        memberRepository.save(member);


        List<RedisOperationDto> redisOperationDtos=new ArrayList<>();


        redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.HashSet, RedisKeyEnum.User_Login_Ip.getKey(),mail,ip));
        redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.HashSet, RedisKeyEnum.User_Login_Count.getKey(),mail,"0"));
        redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.HashSet,RedisKeyEnum.User_Admin.getKey(),String.valueOf(member.getMember_id()),userAdmin.name()));
        redisUtills.Make_Redis_Pipeline(redisOperationDtos);

        /*edisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection stringRedisConnection=(StringRedisConnection) connection;

                stringRedisConnection.hSetNX("user_login_ip",mail,ip);
                stringRedisConnection.hSetNX("user_login_count",mail,"0");

                return null;
            }
        });*/




        return ResponseEntity.ok(ApiResponseCreator.success("로그인성공", StateEnum.Success_Normally.getStates()));

    }

    public Optional<Member> Member_Login2(String mail, String password){

        Optional<Member> member=memberRepository.Check_User_Exist(mail);


        if(member.isPresent()){

            if(bCryptPasswordEncoder.matches(password,member.get().getPassword())){

              return member;

            }


            Optional<Member> member_for_fail=Optional.of(new Member(mail,"",UserAdmin.ROLE_User,MemberPlatForm.Normal));
            return member_for_fail;
        }
        else{



        return member;
        }
    }

    public ResponseEntity<ApiResponseCreator<String>> Member_Login(String mail, String password,String ip){

        Optional<Member> member=memberRepository.Check_User_Exist(mail);
        if(member.isPresent()&&member.get().memberPlatForm.name().equals("Normal")){


            if(bCryptPasswordEncoder.matches(password,member.get().password)/*BCrypt.checkpw(password,member.get().password)*/){


                Jwt jwt=jwtProviders.gen_token(member.get().getMember_id(),member.get().getUserAdmin());

                HttpHeaders headers=cookieUtils.Make_Header_With_Cookie(jwt.getAccess_token());



                return new ResponseEntity<>(ApiResponseCreator.success("로그인 성공",StateEnum.Success_Normally.getStates()),headers,HttpStatus.OK);

            }


        }




        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_No_Exist_Member.getStates()),HttpStatus.BAD_REQUEST);

    }





    public ResponseEntity<ApiResponseCreator<String>> Update_Member_Password(Long member_id, String password){


        Optional<Member> member=memberRepository.findById(member_id);


        log.info("값:{}",member.get().memberPlatForm.name());
        if(Check_Member_Social_Login(member.get())){

        member.get().setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));
        memberRepository.save(member.get());

        return new ResponseEntity<>(ApiResponseCreator.success("success",StateEnum.Success_Normally.getStates()),HttpStatus.OK
        );}


        return new ResponseEntity<>(ApiResponseCreator.fail(StateEnum.Fail_Normally.getStates()),HttpStatus.BAD_REQUEST);


    }




    public ResponseEntity<ApiResponseCreator<String>> Member_LogOut(){


        return new ResponseEntity<>(ApiResponseCreator.success("성공적 로그아웃",StateEnum.Success_Normally.getStates()),cookieUtils.Make_Header_With_Cookie_Logout(),HttpStatus.OK);

    }


    private boolean Check_Member_Social_Login(Member member){

        if(member.getMemberPlatForm().name().equals("Normal")){
            return true;
        }


        return false;


    }
}
