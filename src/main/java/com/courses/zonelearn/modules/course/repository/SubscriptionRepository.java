package com.courses.zonelearn.modules.course.repository;

import com.courses.zonelearn.modules.course.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
}
