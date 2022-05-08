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
public class MutateFollowService {

    private final UserFollowerRepository followRepository;
    private final UserRepository userRepository;

    private final EventPublisher eventPublisher;

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

        eventPublisher.publish(
                USER.getName(),
                singletonList(
                        new UserFollowedEvent(
                                userId,
                                clientId,
                                LocalDateTime.now()
                        )
                )
        );
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

        eventPublisher.publish(USER.getName(),
                singletonList(
                        new UserUnfollowedEvent(
                                userId,
                                clientId,
                                LocalDateTime.now()
                        )
                ));
    }

    private void requireUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(
                    "user with id " + id + " was not found"
            );
        }
    }
}
