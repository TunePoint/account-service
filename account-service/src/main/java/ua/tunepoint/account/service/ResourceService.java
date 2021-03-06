package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.service.client.ResourceClient;
import ua.tunepoint.account.service.client.mapper.ResourceMapper;
import ua.tunepoint.account.model.response.domain.Resource;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceClient client;

    private final ResourceMapper resourceMapper;

    @NotNull
    public Optional<Resource> getImage(String id) {
        if (id == null) {
            return Optional.empty();
        }

        var response = client.getImage(id);
        if (response == null || response.getBody() == null || response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            MDC.put("Id", id);
            log.warn("Image was not found");

            return Optional.empty();
        }

        return Optional.ofNullable(resourceMapper.toResource(response.getBody().getPayload()));
    }
}
