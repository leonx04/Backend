package com.example.backend.Library.service.interfaces.excel.customer;

import com.example.backend.Library.model.entity.customer.Customer;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface ICustomerExcelService {

    String exportCustomerExcelFile(List<Customer> customers, Path filePath) throws IOException;

    void importCustomerExcelFile(InputStream input) throws IOException;

}
