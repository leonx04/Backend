package com.example.backend.Library.model.entity.orders;

import com.example.backend.Library.model.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "OrderStatusLog")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;

}
