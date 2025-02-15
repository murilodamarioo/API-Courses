package com.courses.zonelearn.factory;

import com.courses.zonelearn.modules.course.entities.Course;
import com.courses.zonelearn.modules.course.enums.Status;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

public class CourseFactory {

    private static final Faker faker = new Faker();

    public static Course makeCourse() {
        return makeCourse(UUID.randomUUID(), faker.book().title(), faker.name().title(), Status.ACTIVE, LocalDateTime.now(ZoneId.systemDefault()));
    }

    public static Course makeCourse(UUID id, String courseName, String category, Status status, LocalDateTime now) {
        return Course.builder()
                .id(id)
                .name(courseName)
                .category(category)
                .status(status)
                .updatedAt(now)
                .build();
    }
}

