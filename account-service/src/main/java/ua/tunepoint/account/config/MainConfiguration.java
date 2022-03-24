package ua.tunepoint.account.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import ua.tunepoint.account.service.client.FeignMarker;

@Configuration
@EnableFeignClients(basePackageClasses = FeignMarker.class)
public class MainConfiguration {

}