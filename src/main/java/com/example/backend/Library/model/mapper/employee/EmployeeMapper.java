package com.example.backend.Library.model.mapper.employee;

import com.example.backend.Library.model.dto.request.employee.EmployeeRequest;
import com.example.backend.Library.model.entity.employee.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployeeRequest(EmployeeRequest request);

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "passWord", ignore = true)
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "note", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Employee updateEmployee(EmployeeRequest request);
}
