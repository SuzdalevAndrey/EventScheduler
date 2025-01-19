package ru.andreyszdlv.eventscheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.andreyszdlv.eventscheduler.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public long getCurrentUserId() {
        return userRepository.findByEmail(getCurrentUserEmail()).get().getId();
    }

}
