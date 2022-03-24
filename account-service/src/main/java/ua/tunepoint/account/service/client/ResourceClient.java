package ua.tunepoint.account.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import ua.tunepoint.resource.api.ResourceEndpoint;

@FeignClient(name = "resource-service")
public interface ResourceClient extends ResourceEndpoint {
}
