package com.courses.zonelearn.modules.user.repository;

import com.courses.zonelearn.modules.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
