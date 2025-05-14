package drinkselector.drinks.Serveices;


import drinkselector.drinks.Entity.Drinks;
import drinkselector.drinks.Entity.Image;
import drinkselector.drinks.Etcs.ApiResponseCreator;
import drinkselector.drinks.Etcs.Enums.StateEnum;
import drinkselector.drinks.Etcs.TxtFilter;
import drinkselector.drinks.Repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ResourceLoader resourceLoader;
    private final ImageRepository imageRepository;

    @Value("${spring.image.default_src}")
    private String default_img_src;


    private static final Tika tika = new Tika();

    public List<String> Save_Image(List<MultipartFile> img_list, Drinks drinks) {
        List<String> img_names = new ArrayList<>();
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;

                img_list.stream().forEach(x -> {

                    try (InputStream inputStream = x.getInputStream()) {
                        String mimetype = tika.detect(inputStream);
                        if (((mimetype.equals("image/jpg") || mimetype.equals("image/png") || mimetype.equals("image/gif")) && TxtFilter.file_end_name_filter(x.getName()))) {
                            String img_id = UUID.randomUUID().toString();
                            Image img = new Image(img_id, TxtFilter.file_name_filter(x.getName()), drinks);

                            File destination = new File(img.getImage_name());
                            x.transferTo(destination);  // 파일 저장
                            imageRepository.save(img);
                            stringRedisConnection.hSet("img_src", img.getImage_id(), img.getImage_name());
                            img_names.add(img_id);


                        }

                        throw new Exception();
                    } catch (Exception e) {
                        String img_id = UUID.randomUUID().toString();
                        Image img = new Image(img_id, TxtFilter.file_name_filter(x.getName()), drinks);
                        stringRedisConnection.hSet("img_src", img.getImage_id(), default_img_src);
                        img_names.add(img_id);
                        //여기에 기본 디폴트 이미지를 넣는거로 바꾸는게 어떨까싶다.
                        //따로 로직을 작성해줘야될듯?
                        //throw new RuntimeException();
                    }


                });

                return img_names;
            }
        });

        return img_names;
    }


    public List<Image> Get_Images(Long drink_id){

        return imageRepository.Get_Image_In_Description(drink_id);
    }

}

