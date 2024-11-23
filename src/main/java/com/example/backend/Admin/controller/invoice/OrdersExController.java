package com.example.backend.Admin.controller.invoice;

import com.example.backend.Library.model.entity.orders.Order;
import com.example.backend.Library.service.impl.excel.orders.OrdersExcel;
import com.example.backend.Library.service.impl.orders.OrderImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/orders/export/excel")
public class OrdersExController {
    @Autowired
    private OrderImpl order;
    @GetMapping()
    public void exportToExcel(HttpServletResponse response ) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        DateFormat dateFormatter = new SimpleDateFormat("dd_MM_yyyy");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Danh_Sach_Hoa_Don_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Order> listUsers = order.listAll();

        OrdersExcel excelExporter = new OrdersExcel(listUsers);

        excelExporter.export(response);
    }
}
