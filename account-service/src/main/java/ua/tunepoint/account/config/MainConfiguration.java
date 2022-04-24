package ua.tunepoint.account.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ua.tunepoint.account.service.client.FeignMarker;
import ua.tunepoint.web.exception.WebExceptionHandler;

@Configuration
@EnableScheduling
@EnableFeignClients(basePackageClasses = FeignMarker.class)
public class MainConfiguration {

    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new WebExceptionHandler();
    }
}