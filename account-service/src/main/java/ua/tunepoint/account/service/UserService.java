package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.account.data.mapper.UserMapper;
import ua.tunepoint.account.data.repository.UserRepository;
import ua.tunepoint.account.model.response.payload.UserPrivatePayload;
import ua.tunepoint.account.model.response.payload.UserPublicPayload;
import ua.tunepoint.account.service.support.ProfileSmartMapper;
import ua.tunepoint.web.exception.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final FollowService followService;
    private final ProfileService profileService;

    private final UserRepository userRepository;

    private final ProfileSmartMapper profileSmartMapper;
    private final UserMapper userMapper;

    public UserPublicPayload findUserPublic(Long userId, Long clientId) {
        var user = requireUser(userId);
        return userMapper.toPublicUser(
                user,
                profileService.findById(user.getId()),
                isFollowing(clientId, userId)
        );
    }

    public UserPrivatePayload findUserPrivate(Long userId) {
        var user = requireUser(userId);
        return userMapper.toPrivateUser(
                user,
                profileService.findById(user.getId())
        );
    }

    @Transactional
    public void create(Long id, String username, String email) {
        var user = userRepository.save(
            User.create(
                    id, username, email
            )
        );
    }

    private User requireUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user with id " + id + " was not found"));
    }

    public List<UserPublicPayload> findBulk(List<Long> ids, Long clientId) {
        return userRepository.findBulk(ids)
                .stream().map(user -> userMapper.toPublicUser(
                        user, profileSmartMapper.toPayload(user.getProfile()), isFollowing(clientId, user.getId()))
                )
                .sorted(Comparator.comparing(it -> ids.indexOf(it.getId())))
                .collect(Collectors.toList());
    }

    private boolean isFollowing(Long clientId, Long userId) {
        if (clientId == null) {
            return false;
        }
        return followService.isFollowing(clientId, userId);
    }
}
