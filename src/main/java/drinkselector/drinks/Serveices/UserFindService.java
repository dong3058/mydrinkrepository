package drinkselector.drinks.Serveices;



import drinkselector.drinks.Dtos.Show.DrinkShowDto;
import drinkselector.drinks.Dtos.Show.RecentSearchShowDto;

import drinkselector.drinks.Entity.ThumbNail;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.RedisUtill.ReCentSearchLog;
import drinkselector.drinks.Repository.ThumbRepository;
import lombok.RequiredArgsConstructor;
import org.nd4j.linalg.api.ops.Op;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFindService {


    private final RedisTemplate<String,String> redisTemplate;

    private final ReCentSearchLog reCentSearchLog;

    private final ThumbRepository thumbRepository;

    public ResponseEntity<ApiResponseCreator<List<DrinkShowDto>>> Get_User_Find_List(Long user_id){

        Map<Object,Object> maps=redisTemplate.opsForHash().entries("%s_list".formatted(user_id));


       Set<Object> keys=maps.keySet();

       List<DrinkShowDto> userFindDtos=keys.stream().map(x->{

          Long drink_id=Long.parseLong((String) x);

          String drink_name=(String)maps.get(x);
           Optional<ThumbNail> thumbNail=thumbRepository.find_by_drink_id(drink_id);

          return new DrinkShowDto(drink_id,thumbNail.get().getThumb_path(),drink_name);

       }).collect(Collectors.toList());



       // List<UserFindDto> userFindDtos=userFindRepository.Find_User_Choose_List(user_id);



        return ResponseEntity.ok(ApiResponseCreator.success(userFindDtos, StateEnum.Success_Normally.getStates()));

    }


    public ResponseEntity<ApiResponseCreator<String>> Add_User_List(Long user_id,Long drink_id,String drink_name){




        redisTemplate.execute(new SessionCallback<Object>() {
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
        });



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


        redisTemplate.execute(new SessionCallback<Object>() {

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
        });




        return ResponseEntity.ok(ApiResponseCreator.success("성공", StateEnum.Success_Normally.getStates()));

       /* Optional<UserFind> userFind=userFindRepository.Find_Exist_List(user_id,drink_id);
        if(userFind.isPresent()) {
            userFind.get().setSign_delete(true);


            return ResponseEntity.ok(ApiResponseCreator.success("성공", StateEnum.Success_Normally.getStates()));
        }

        throw new RuntimeException();*/
    }




    public ResponseEntity<ApiResponseCreator<List<RecentSearchShowDto>>> get_recent_search_log(Long member_id){



        return ResponseEntity.ok(ApiResponseCreator.success(reCentSearchLog.get_recent_search_log(member_id),StateEnum.Success_Normally.getStates()));
    }
}
