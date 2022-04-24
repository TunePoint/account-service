package ua.tunepoint.account.service.client.mapper;

import org.mapstruct.Mapper;
import ua.tunepoint.account.model.response.domain.Resource;
import ua.tunepoint.resource.model.response.payload.ImageResourcePayload;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

   Resource toResource(ImageResourcePayload payload);
}
