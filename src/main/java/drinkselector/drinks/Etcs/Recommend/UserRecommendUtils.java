package drinkselector.drinks.Etcs.Recommend;


import drinkselector.drinks.Dtos.DrinkDto;
import drinkselector.drinks.Dtos.Show.DrinkShowDto;
import drinkselector.drinks.Entity.Drinks;
import drinkselector.drinks.Entity.ThumbNail;
import drinkselector.drinks.Repository.DrinkRepository;
import drinkselector.drinks.Repository.ThumbRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class UserRecommendUtils {


    private final DrinkRepository drinkRepository;

    private final RedisTemplate<String,String> redisTemplate;


    private final ThumbRepository thumbRepository;

    private IndArraySplit Get_Drink_Array(){

        String drink_feature_table=redisTemplate.opsForValue().get("drink_feature_table");

       // INDArray drink_feature_arr=Nd4j.fromByteArray(Base64.getDecoder().decode(drink_feature_table));

        INDArray drink_feature_arr=Nd4j.create(drink_feature_table);

        IndArraySplit indArraySplit=new IndArraySplit(drink_feature_arr.getColumn(0).reshape(drink_feature_arr.size(0),1),drink_feature_arr.getColumns(1,5));

        return indArraySplit;

    }
    @PostConstruct
    @Scheduled(cron="0 30 1 * * *")
    private void Update_Drink_Array(){
        List<Drinks> drinks=drinkRepository.findAll();
        if(drinks.size()>0) {


            double[][] drinks_arr = new double[drinks.size()][6];
            redisTemplate.executePipelined(new RedisCallback<Object>() {


                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;


                    for (int i = 0; drinks.size() > i; i++) {


                        Drinks drink = drinks.get(i);

                        stringRedisConnection.hSetNX("drink", String.valueOf(drink.getDrink_id()), "%s-%s-%s".formatted(String.valueOf(drink.getDrink_id()), drink.getDrink_name(), drink.getDrinkType().name()));
                        drinks_arr[i][0] = drink.getDrink_id();
                        drinks_arr[i][1] = drink.getSweat();
                        drinks_arr[i][2] = drink.getDry();
                        drinks_arr[i][3] = drink.getSharp();
                        drinks_arr[i][4] = drink.getBitter();
                        drinks_arr[i][5] = drink.getBody();


                    }


                    return null;
                }
            });


        /*for(int i=0;drinks.size()>i;i++){


               Drinks drink=drinks.get(i);


               redisTemplate.opsForHash().putIfAbsent("drink",drink.getDrink_id(),"%s-%s".formatted(drink.getDrink_name(),drink.getDrinkType().name()));
               drinks_arr[i][0]=drink.getDrink_id();
               drinks_arr[i][1]=drink.getSweat();
               drinks_arr[i][2]=drink.getDry();
               drinks_arr[i][3]=drink.getSharp();
               drinks_arr[i][4]=drink.getBitter();
               drinks_arr[i][5]=drink.getBody();


        }*/
            INDArray arrs = Nd4j.create(drinks_arr);

            redisTemplate.opsForValue().set("drink_feature_byte", arrs.toString());

     /*  try {
          byte[] drink_feature_byte=Nd4j.toByteArray(Nd4j.create(drinks_arr));
          String base64_encoding=Base64.getEncoder().encodeToString(drink_feature_byte);
          redisTemplate.opsForValue().set("drink_feature_byte",base64_encoding);

       }
       catch (Exception e){

           throw new RuntimeException();
       }*/
        }
    }




    private Double Get_Norm_As_Scalar(INDArray arr){

       return arr.norm2Number().doubleValue();


    }

    private INDArray Get_Norm_As_Vector(INDArray arr){

        INDArray result=arr.norm2(0);

        return result.reshape(result.size(0),1);


    }

    private INDArray Get_Cosine_Similar(double [] user_input){

        INDArray user_input_arr=Nd4j.create(user_input).reshape(5,1);

        IndArraySplit drink_arr_split=Get_Drink_Array();


        double user_input_norm=Get_Norm_As_Scalar(user_input_arr);


        INDArray drink_feature_norm=Get_Norm_As_Vector(drink_arr_split.getDrink_feature_arr());

        INDArray inner_product=drink_arr_split.getDrink_feature_arr().mmul(user_input_arr);

        return Nd4j.hstack(drink_arr_split.getDrink_id_arr(),inner_product.div(drink_feature_norm.mul(user_input_norm)));



    }

    public List<DrinkShowDto> Get_User_Recommend_Drink(double [] user_input,String drink_type){

        INDArray arr=Get_Cosine_Similar(user_input);
        List<RecommendDrink> recommendDrinks=new ArrayList<>();

        for(int i=0;arr.size(0)>i;i++){

            INDArray row=arr.getRow(i);
            RecommendDrink recommendDrink=new RecommendDrink(row.getLong(0),row.getLong(1));

            recommendDrinks.add(recommendDrink);


        }


        List<Object> recommendDrinks_before_edit=redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                StringRedisConnection stringRedisConnection=(StringRedisConnection) connection;

                recommendDrinks.stream().sorted().forEach(x->{
                        stringRedisConnection.hGet("drink",String.valueOf(x.getDrink_id()));



                });
                return null;
            }
        });




        List<Long> li=new ArrayList<>();

        List<DrinkShowDto> drinkDtos=recommendDrinks_before_edit.stream().filter(x-> {
            String [] values=x.toString().split("-");


            if(values[2].equals(drink_type)){


                return true;
            }

            return false;


        }).map(x->{
            String [] values=x.toString().split("-");
            Long drink_id=Long.parseLong(values[0]);
            li.add(drink_id);
            return new DrinkShowDto(drink_id,"",values[1]);

            //Optional<ThumbNail> t=thumbRepository.findById(drink_id);

            /*if(t.isPresent()){

                return new DrinkShowDto(drink_id,t.get().getThumb_path(),values[1]);

            }*/

            //default값을 정해놔야겟는대
            //return new DrinkShowDto(drink_id,t.get().getThumb_path(),values[1]);




        }).limit(5l).sorted().collect(Collectors.toList());


        List<ThumbNail> thumbNails=thumbRepository.find_by_drink_id(li);

        IntStream.range(0,thumbNails.size())
                .forEach(x->{

                    DrinkShowDto drinkShowDto=drinkDtos.get(x);

                    drinkShowDto.setThumbnail_path(thumbNails.get(x).getImage_path());


                });



        return drinkDtos;


    }
}
