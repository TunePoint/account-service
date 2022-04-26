package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.account.data.entity.UserFollower;
import ua.tunepoint.account.data.entity.UserFollowerId;
import ua.tunepoint.account.data.mapper.UserMapper;
import ua.tunepoint.account.data.repository.UserFollowerRepository;
import ua.tunepoint.account.data.repository.UserRepository;
import ua.tunepoint.account.model.response.payload.FollowPayload;
import ua.tunepoint.account.model.response.payload.UserPublicPayload;
import ua.tunepoint.account.service.support.ProfileSmartMapper;
import ua.tunepoint.web.exception.BadRequestException;
import ua.tunepoint.web.exception.NotFoundException;

import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserFollowerRepository followRepository;
    private final UserRepository userRepository;

    private final ProfileSmartMapper profileSmartMapper;
    private final UserMapper userMapper;

    @Transactional
    public void follow(Long userId, Long clientId) {
        var followId = new UserFollowerId(userId, clientId);

        if (Objects.equals(userId, clientId)) {
            throw new BadRequestException(
                    "user can't follow self"
            );
        }

        if (followRepository.existsById(followId)) {
            throw new BadRequestException(
                    "user with id " + clientId + " is already following user with id " + userId
            );
        }

        Stream.of(userId, clientId).forEach(this::requireUserExists);

        var link = new UserFollower();
        link.setId(followId);

        followRepository.save(link);
    }

    @Transactional
    public void unfollow(Long userId, Long clientId) {
        var followId = new UserFollowerId(userId, clientId);

        if (Objects.equals(userId, clientId)) {
            throw new BadRequestException(
                    "user can't follow self"
            );
        }

        if (!followRepository.existsById(followId)) {
            throw new BadRequestException(
                    "user with id " + clientId + " is already not following user with id " + userId
            );
        }

        Stream.of(userId, clientId).forEach(this::requireUserExists);

        followRepository.deleteById(followId);
    }

    public Page<FollowPayload> findFollowers(Long userId, Pageable pageable) {
        return followRepository.findFollowers(userId, pageable)
                .map(link -> new FollowPayload(
                                userMapper.toPublicUser(
                                        link.getFollower(),
                                        profileSmartMapper.toPayload(link.getFollower().getProfile())
                                ),
                                link.getFollowingDate()
                        )
                );
    }

    public Page<FollowPayload> findWhoFollows(Long userId, Pageable pageable) {
        return followRepository.findWhoFollows(userId, pageable)
                .map(link -> new FollowPayload(
                                userMapper.toPublicUser(
                                        link.getUser(),
                                        profileSmartMapper.toPayload(link.getUser().getProfile())
                                ),
                                link.getFollowingDate()
                        )
                );
    }

    private void requireUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(
                    "user with id " + id + " was not found"
            );
        }
    }
}
