package com.example.backend.Library.model.dto.reponse;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class PageableResponse {
     Long totalElements; // Tổng số phần tử
     int pageSize;      // Kích thước trang
     int totalPages;    // Tổng số trang
     int pageNo;        // Số trang hiện tại
     Object content;     // Nội dung của trang
}
//    int pageNumber;