package ua.tunepoint.model.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountDomain {

    USER("user"), PROFILE("profile");

    private final String name;
}
