package ua.tunepoint.account.data;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import ua.tunepoint.account.data.entity.Role;
import ua.tunepoint.account.data.repository.RoleRepository;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer implements ApplicationListener<ApplicationStartedEvent> {

    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        for (var role: Roles.values()) {
            if (!roleRepository.existsByName(role.name())) {
                roleRepository.save(new Role(role.id(), role.name()));
            }
        }
    }
}
