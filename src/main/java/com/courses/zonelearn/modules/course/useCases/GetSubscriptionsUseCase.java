package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.entities.Subscription;
import com.courses.zonelearn.modules.course.repository.SubscriptionRepository;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import com.courses.zonelearn.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetSubscriptionsUseCase {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTProvider jwtProvider;

    public List<Subscription> execute(UUID userId) {
        List<Subscription> subscriptions;

        this.userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        return this.subscriptionRepository.findByUserId(userId);
    }
}
