package com.example.backend.Library.model.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindByOrderStatusAndOrderType {
    private Integer orderStatus;
    private String orderType;
}
