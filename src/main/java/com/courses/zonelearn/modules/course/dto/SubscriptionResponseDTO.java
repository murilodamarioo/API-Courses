package com.courses.zonelearn.modules.course.dto;

import com.courses.zonelearn.modules.course.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "c36fd087-1282-4627-b57a-06e904f2cc24")
    private UUID courseId;

    @Schema(example = "ACTIVE")
    private Status status;

    private LocalDateTime subscriptionDate;

}
