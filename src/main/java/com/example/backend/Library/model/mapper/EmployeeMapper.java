package com.example.backend.Library.model.mapper;

import com.example.backend.Library.model.dto.request.EmployeeRequest;
import com.example.backend.Library.model.dto.response.EmployeeResponse;
import com.example.backend.Library.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeRequest request);
    EmployeeResponse toEmployeeResponse(Employee employee);

    void updateEmployee(@MappingTarget Employee employee, EmployeeRequest request);
}
