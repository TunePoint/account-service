package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tunepoint.account.data.entity.UserFollower;
import ua.tunepoint.account.data.entity.UserFollowerId;
import ua.tunepoint.account.data.mapper.UserMapper;
import ua.tunepoint.account.data.repository.UserFollowerRepository;
import ua.tunepoint.account.data.repository.UserRepository;
import ua.tunepoint.account.model.event.user.UserFollowedEvent;
import ua.tunepoint.account.model.event.user.UserUnfollowedEvent;
import ua.tunepoint.account.model.response.payload.FollowPayload;
import ua.tunepoint.account.service.support.ProfileSmartMapper;
import ua.tunepoint.event.starter.publisher.EventPublisher;
import ua.tunepoint.web.exception.BadRequestException;
import ua.tunepoint.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static ua.tunepoint.account.model.event.AccountDomain.USER;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final UserFollowerRepository followRepository;
    private final UserRepository userRepository;

    private final ProfileSmartMapper profileSmartMapper;
    private final UserMapper userMapper;

    public Page<FollowPayload> findFollowers(Long userId, Pageable pageable, Long clientId) {
        return followRepository.findFollowers(userId, pageable)
                .map(link -> new FollowPayload(
                                userMapper.toPublicUser(
                                        link.getFollower(),
                                        profileSmartMapper.toPayload(link.getFollower().getProfile()),
                                        isFollowing(clientId, link.getUser().getId())
                                ),
                                link.getFollowingDate()
                        )
                );
    }

    public Page<FollowPayload> findWhoFollows(Long userId, Pageable pageable, Long clientId) {
        return followRepository.findWhoFollows(userId, pageable)
                .map(link -> new FollowPayload(
                                userMapper.toPublicUser(
                                        link.getUser(),
                                        profileSmartMapper.toPayload(link.getUser().getProfile()),
                                        isFollowing(clientId, link.getUser().getId())
                                ),
                                link.getFollowingDate()
                        )
                );
    }

    public boolean isFollowing(Long userId, Long authorId) {
        if (userId == null) {
            return false;
        }
        return followRepository.existsById(new UserFollowerId(authorId, userId));
    }

    private void requireUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(
                    "user with id " + id + " was not found"
            );
        }
    }
}
