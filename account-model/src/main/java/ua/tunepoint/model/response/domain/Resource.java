package ua.tunepoint.model.response.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    private String id;
    private String url;
    private Long size;
    private LocalDateTime created;
}
