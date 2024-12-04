package com.example.backend.Library.model.dto.response.attributes;

import com.example.backend.Library.model.entity.employee.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString
@SuperBuilder
public class AttributeResponse {
     String id;
    String name;
    Long appliedProductCount;
    LocalDate createdAt;
    Employee createdBy;//Cần chuyển thành EmployeeResponse để bảo mật hơn.
    List<String> listNameSuggestions;
}
