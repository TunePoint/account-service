package ua.tunepoint.account.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import ua.tunepoint.account.service.client.config.ClientConfiguration;

@FeignClient(name = "resource-service", decode404 = true, configuration = ClientConfiguration.class)
public interface ResourceClient extends CompositeResourceClient {
}
