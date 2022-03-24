package ua.tunepoint.account.utils;

import lombok.experimental.UtilityClass;
import ua.tunepoint.model.response.CommonResponse;
import ua.tunepoint.model.response.StatusResponse;

@UtilityClass
public class ResponseUtils {

    public static StatusResponse constructResponse(Exception ex, int status) {
        return StatusResponse.builder()
                .status(status)
                .message(ex.getMessage())
                .build();
    }
}
