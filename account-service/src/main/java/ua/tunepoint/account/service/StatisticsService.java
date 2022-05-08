package ua.tunepoint.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.tunepoint.account.data.repository.UserStatisticsRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsService {

    private final UserStatisticsRepository repository;

    @Transactional
    public void incrementAudioCount(Long userId) {
        repository.incrementAudioCount(userId);
    }

    @Transactional
    public void decrementAudioCount(Long userId) {
        repository.decrementAudioCount(userId);
    }
}
