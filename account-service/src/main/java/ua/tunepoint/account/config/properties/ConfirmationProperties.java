package ua.tunepoint.account.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Data
@Valid
@Component
@ConfigurationProperties(prefix = "confirmation")
public class ConfirmationProperties {

    @NotNull
    private Integer attempts;

    @NotNull
    private CodeProperties code;

    private EmailProperties email;

    @Data
    public static class CodeProperties {

        @NotNull
        private Duration duePeriod;

        @NotNull
        private Duration blockResendPeriod;

        @NotNull
        private Integer length;
    }

    @Data
    public static class EmailProperties {

        @NotNull
        private String template;

        @NotNull
        private String subject;
    }
}
