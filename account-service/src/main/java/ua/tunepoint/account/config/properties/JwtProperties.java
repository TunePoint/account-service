package ua.tunepoint.account.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Valid
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @NotNull
    private String issuer;

    /**
     * in milliseconds
     */
    @Min(0)
    private Long refreshTokenTtl;

    /**
     * in millliseconds
     */
    @Min(0)
    private Long accessTokenTtl;
}
