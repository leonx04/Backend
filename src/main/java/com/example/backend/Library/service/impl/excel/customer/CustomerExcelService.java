package com.example.backend.Library.service.impl.excel.customer;

import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.repository.customer.CustomerRepository;
import com.example.backend.Library.service.interfaces.excel.customer.ICustomerExcelService;
import com.example.backend.Library.util.FilePathUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerExcelService implements ICustomerExcelService {

    private final CustomerRepository customerRepository;

    public CustomerExcelService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public String exportCustomerExcelFile(List<Customer> customers, Path filePath) throws IOException {
        String fileName;
        try (Workbook workbook = new XSSFWorkbook()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd - HHmmss");
            String timeString = sdf.format(new Date());
            Sheet sheet = workbook.createSheet("Customers - " + timeString);

            CellStyle headerCellStyle = workbook.createCellStyle(); // Tạo style cho header
            CellStyle dataCellStyle = workbook.createCellStyle(); // Tạo style cho dữ liệu // Tạo style
            Font headerFont = workbook.createFont(); // Tạo font
            customStyle(headerCellStyle, dataCellStyle, headerFont);

            // Tạo tiêu đề cột
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Avatar", "Mã khách hàng", "Họ tên", "Email", "Số điện thoại", "Ngày sinh"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerCellStyle);
            }

            // Điền dữ liệu khách hàng vào sheet
            int rowIndex = 1;
            for (Customer customer : customers) {
                Row row = sheet.createRow(rowIndex++);
                mapCustomerToRow(customer, row);
                for (int i = 0; i < headers.length; i++) {
                    row.getCell(i).setCellStyle(dataCellStyle);
                }
            }

            fileName = "customers - " + timeString + ".xlsx";

            // Thiết lập đường dẫn mặc định nếu không có filePath
            if (filePath == null) {
                filePath = FilePathUtil.getDefaultSavePath(fileName);
            }

            // Tạo thư mục nếu chưa tồn tại
            if (!filePath.toFile().getParentFile().exists()) {
                filePath.toFile().getParentFile().mkdirs();
            }

            // Ghi workbook vào file
            try (FileOutputStream fileOut = new FileOutputStream(filePath.toString())) {
                workbook.write(fileOut);
            }

        }

        return filePath.toAbsolutePath().toString();
    }

    @Override
    public void importCustomerExcelFile(InputStream input) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(input)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên
            List<Customer> customers = StreamSupport
                    .stream(sheet.spliterator(), false)
                    .skip(1) // Bỏ qua header row
                    .map(row -> mapRowToCustomer(row)) // Chuyển row thành Customer
                    .collect(Collectors.toList()); // Add vào list

            customerRepository.saveAll(customers);
        }
    }

    private void customStyle (CellStyle headerCellStyle, CellStyle dataCellStyle, Font headerFont) {
        // Thiết lập style cho header
        headerFont.setBold(true); // Đặt in đậm
        headerCellStyle.setFont(headerFont); // Đặt font cho style
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER); // Căn giữa
        headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // Căn giữa
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN); // Đường viền dưới

        // Thiết lập style cho dữ liệu
        dataCellStyle.setBorderTop(BorderStyle.THIN);
        dataCellStyle.setBorderBottom(BorderStyle.THIN);
        dataCellStyle.setBorderLeft(BorderStyle.THIN);
        dataCellStyle.setBorderRight(BorderStyle.THIN);
    }

    private void mapCustomerToRow(Customer customer, Row row) {
        row.createCell(0).setCellValue(customer.getId());
        row.createCell(1).setCellValue(customer.getImageURL());
        row.createCell(2).setCellValue(customer.getCode());
        row.createCell(3).setCellValue(customer.getFullName());
        row.createCell(4).setCellValue(customer.getEmail());
        row.createCell(5).setCellValue(customer.getPhone());
        row.createCell(6).setCellValue(customer.getBirthDate().toString());
    }

    private Customer mapRowToCustomer(Row row) {
        Customer customer = new Customer();

        customer.setId((int) row.getCell(0).getNumericCellValue());
        customer.setImageURL(row.getCell(1).getStringCellValue());
        customer.setCode(row.getCell(2).getStringCellValue());
        customer.setFullName(row.getCell(3).getStringCellValue());
        customer.setEmail(row.getCell(4).getStringCellValue());
        customer.setPhone(row.getCell(5).getStringCellValue());
        customer.setBirthDate(row.getCell(6).getLocalDateTimeCellValue().toLocalDate());

        return customer;
    }

}
