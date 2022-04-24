package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.tunepoint.account.config.properties.ConfirmationProperties;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class RandomConfirmationCodeProvider implements ConfirmationCodeProvider {

    private static final String[] DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private final ConfirmationProperties props;

    @Override
    public String generate() {
        var random = new Random();
        return IntStream.range(0, props.getCode().getLength())
                .mapToObj(it -> randomDigit(random))
                .collect(Collectors.joining());
    }

    private static String randomDigit(Random random) {
        return DIGITS[random.nextInt(DIGITS.length)];
    }
}
