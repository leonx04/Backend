package com.example.backend.Library.model.dto.request.order;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UpdateStatus {
    private Integer orderStatus;
    private LocalDateTime updatedAt;
}
