package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.account.data.mapper.UserMapper;
import ua.tunepoint.account.data.repository.UserRepository;
import ua.tunepoint.account.exception.NotFoundException;
import ua.tunepoint.model.response.payload.UserBasePayload;
import ua.tunepoint.model.response.payload.UserFullPayload;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserBasePayload findUserBase(Long id) {
        return findAndMap(id, userMapper::toUserBase);
    }

    public UserFullPayload findUserFull(Long id) {
        return findAndMap(id, userMapper::toUserFull);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    private <T> T findAndMap(Long id, Function<User, T> mapper) {
        return userRepository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " was not found"));
    }
}
