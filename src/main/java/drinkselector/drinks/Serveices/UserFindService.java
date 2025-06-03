package drinkselector.drinks.Serveices;



import drinkselector.drinks.Dtos.Show.DrinkShowDto;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;

import drinkselector.drinks.Entity.ThumbNail;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.RedisOpEnum;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.RedisUtill.RedisOperationDto;
import drinkselector.drinks.Etcs.RedisUtill.RedisUtills;
import drinkselector.drinks.Repository.ThumbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFindService {



    private final RedisUtills redisUtills;

    private final ThumbRepository thumbRepository;

    public ResponseEntity<ApiResponseCreator<List<DrinkShowDto>>> Get_User_Find_List(Long user_id){

        Map<Object,Object> maps=redisUtills.RedisOpsHashGetEntryOperation("%s_list".formatted(user_id));


       Set<Object> keys=maps.keySet();
       log.info("keys:{}",keys.size());
       List<Long> key_list=keys.stream().map(x->{

           log.info("return_id:{}",x);
           return Long.parseLong((String) x);

       }).toList();



       List<ThumbNail> thumbNails=thumbRepository.find_by_drink_id(key_list);

       List<DrinkShowDto> userFindDtos=thumbNails.stream().map(x->{
           String drink_name=(String) maps.get((x.getDrink_id().toString()));


           return new DrinkShowDto(x.getDrink_id(),x.getImage_path(),drink_name);


       }).toList();


       // List<UserFindDto> userFindDtos=userFindRepository.Find_User_Choose_List(user_id);



        return ResponseEntity.ok(ApiResponseCreator.success(userFindDtos, StateEnum.Success_Normally.getStates()));

    }


    public ResponseEntity<ApiResponseCreator<String>> Add_User_List(Long user_id,Long drink_id,String drink_name){


        List<RedisOperationDto> redisOperationDtos=new ArrayList<>();

        redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.HashSet,"%s_list".formatted(user_id), String.valueOf(drink_id), drink_name));
        redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.SetAdd,String.valueOf(drink_id),String.valueOf(user_id)));

        redisUtills.Make_Redis_Tx(redisOperationDtos);
       /* redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public  Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();

                SetOperations<String,String> sop=operations.opsForSet();
                HashOperations<String,String,String> hop=operations.opsForHash();
                hop.put("%s_list".formatted(user_id), String.valueOf(drink_id), drink_name);
                sop.add(String.valueOf(drink_id),String.valueOf(user_id));
                operations.exec();
                return null;
            }
        });*/



           /* Optional<UserFind> userFind=userFindRepository.Find_Exist_List(user_id,drink_id);

            if(userFind.isEmpty()){
                LocalDateTime now=LocalDateTime.now();
                UserFind userFind1=new UserFind(user_id,drink_id);
                userFind1.setSign_in_date(now);
                userFind1.setUpdate_date(now);
                userFindRepository.save(userFind1);

            }

            userFind.get().setSign_delete(false);*/

            return ResponseEntity.ok(ApiResponseCreator.success("성공",StateEnum.Success_Normally.getStates()));
    }


    public ResponseEntity<ApiResponseCreator<String>> Del_User_List(Long user_id,Long drink_id){


        List<RedisOperationDto> redisOperationDtos=new ArrayList<>();

        redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.HashDelete,"%s_list".formatted(user_id), String.valueOf(drink_id)));
        redisOperationDtos.add(new RedisOperationDto(RedisOpEnum.SetDelete,String.valueOf(drink_id),String.valueOf(user_id)));

        redisUtills.Make_Redis_Tx(redisOperationDtos);


        /*redisTemplate.execute(new SessionCallback<Object>() {

            @Override
            public  Object execute(RedisOperations operations) throws DataAccessException {

                operations.multi();

                SetOperations<String,String> sop=operations.opsForSet();
                HashOperations<String,String,String> hop=operations.opsForHash();
                hop.delete("%s_list".formatted(user_id),drink_id);
                sop.remove(String.valueOf(drink_id),String.valueOf(user_id));
                operations.exec();

                return null;
            }
        });*/




        return ResponseEntity.ok(ApiResponseCreator.success("성공", StateEnum.Success_Normally.getStates()));

       /* Optional<UserFind> userFind=userFindRepository.Find_Exist_List(user_id,drink_id);
        if(userFind.isPresent()) {
            userFind.get().setSign_delete(true);


            return ResponseEntity.ok(ApiResponseCreator.success("성공", StateEnum.Success_Normally.getStates()));
        }

        throw new RuntimeException();*/
    }




    public ResponseEntity<ApiResponseCreator<List<RecentSearchShowDto>>> get_recent_search_log(Long member_id){



        return ResponseEntity.ok(ApiResponseCreator.success(redisUtills.get_recent_search_log(member_id),StateEnum.Success_Normally.getStates()));
    }
}
