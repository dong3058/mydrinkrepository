package drinkselector.drinks.Etcs;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
public class ApiResponseCreator<T> {


    private T data;

    private boolean state;

    private String description;

    public static <T> ApiResponseCreator<T> success(T data, String description){

        return new ApiResponseCreator<>(data,true,description);


    }


    public static <T> ApiResponseCreator<T> fail(String description){

        return new ApiResponseCreator<>(null,false,description);

    }
}
