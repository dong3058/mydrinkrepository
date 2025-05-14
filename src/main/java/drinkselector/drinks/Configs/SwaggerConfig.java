package drinkselector.drinks.Configs;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI(){

        return new OpenAPI()
                .components(new Components())
                .info(ApiInfo());
    }

    public Info ApiInfo(){

        return new Info()
                .title("Api문서")
                .description("주류 APi문서입니다.")
                .version("1.0.0");

    }
}
