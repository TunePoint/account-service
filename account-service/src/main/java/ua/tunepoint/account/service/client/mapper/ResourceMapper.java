package ua.tunepoint.account.service.client.mapper;

import org.mapstruct.Mapper;
import ua.tunepoint.model.response.domain.Media;
import ua.tunepoint.resource.model.response.payload.MediaPayload;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

   Media toMedia(MediaPayload payload);
}
