package com.courses.zonelearn.modules.course.dto;

import com.courses.zonelearn.modules.course.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SubscriptionResponseDTO {

    private UUID courseId;
    private Status status;
    private LocalDateTime subscriptionDate;

}
