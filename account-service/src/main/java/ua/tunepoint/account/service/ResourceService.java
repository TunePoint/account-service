package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.exception.ExternalException;
import ua.tunepoint.account.service.client.ResourceClient;
import ua.tunepoint.account.service.client.mapper.ResourceMapper;
import ua.tunepoint.model.response.domain.Media;
import ua.tunepoint.resource.model.response.payload.MediaPayload;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final ResourceClient client;

    private final ResourceMapper resourceMapper;

    @Nullable
    public Media getMedia(String id) {
        if (id == null) {
            return null;
        }

        var response = client.getResource(id).getBody();
        if (response == null || response.getStatus() != 0) {
            throw new ExternalException("Resource service returned invalid response");
        }

        return resourceMapper.toMedia(response.getPayload());
    }
}
