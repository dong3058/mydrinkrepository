package drinkselector.drinks.Configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;

@Configuration
public class DrinkFeatureTableThread implements SchedulingConfigurer {


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {


        ThreadPoolTaskScheduler threadPoolTaskExecutor=new ThreadPoolTaskScheduler();


        threadPoolTaskExecutor.setPoolSize(1);
        threadPoolTaskExecutor.setThreadNamePrefix("drink_feature_update_task-");
        threadPoolTaskExecutor.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskExecutor);
    }
}
