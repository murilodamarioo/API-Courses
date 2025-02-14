package com.courses.zonelearn.modules.course.useCases;

import com.courses.zonelearn.exceptions.CourseNotFoundException;
import com.courses.zonelearn.modules.course.dto.SubscriptionResponseDTO;
import com.courses.zonelearn.modules.course.entities.Subscription;
import com.courses.zonelearn.modules.course.enums.Status;
import com.courses.zonelearn.modules.course.repository.CourseRepository;
import com.courses.zonelearn.modules.course.repository.SubscriptionRepository;
import com.courses.zonelearn.modules.user.repository.UserRepository;
import com.courses.zonelearn.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateSubscriptionUseCase {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public SubscriptionResponseDTO execute(UUID courseId, UUID userId) {
        this.userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        this.courseRepository.findById(courseId).orElseThrow(
                () -> new CourseNotFoundException("The Id entered does not exist")
        );

        var subscription = Subscription.builder()
                .userId(userId)
                .courseId(courseId)
                .status(Status.ACTIVE).build();

        var response = this.subscriptionRepository.save(subscription);

        return mapToResponseDTO(response);
    }

    private SubscriptionResponseDTO mapToResponseDTO(Subscription subscription) {
        // Map subscription to subscription response dto
        return SubscriptionResponseDTO.builder()
                .courseId(subscription.getCourseId())
                .status(subscription.getStatus())
                .subscriptionDate(subscription.getSubscriptionDate())
                .build();
    }
}
