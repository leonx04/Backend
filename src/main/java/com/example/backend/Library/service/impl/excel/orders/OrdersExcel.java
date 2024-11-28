package com.example.backend.Library.service.impl.excel.orders;

import com.example.backend.Library.model.entity.orders.Order;
import com.example.backend.Library.model.entity.orders.OrderDetail;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrdersExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Order> orders;

    public OrdersExcel(List<Order> orders) {
        this.orders = orders;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Orders");
    }

    private void writeHeader() {
        String[] headers = {
                "Mã HĐ", "Ngày tạo", "Ngày hoàn thành", "Tên KH", "Mã SP", "Tên SP",
                "Số Lượng", "Đơn Giá", "Thành Tiền", "Voucher", "Tổng Tiền",
                "Người nhận", "Địa chỉ giao hàng", "SĐT"
        };
        Row headerRow = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);
        // Căn giữa chữ trong ô
        style.setAlignment(HorizontalAlignment.CENTER); // Căn giữa theo chiều ngang
        style.setVerticalAlignment(VerticalAlignment.CENTER); // Căn giữa theo chiều dọc
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value != null) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue("");
        }
        // Căn giữa chữ trong ô
        style.setAlignment(HorizontalAlignment.CENTER); // Căn giữa theo chiều ngang
        style.setVerticalAlignment(VerticalAlignment.CENTER); // Căn giữa theo chiều dọc
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowIdx = 1;

        // Tạo style nền trắng
        CellStyle styleWhite = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        styleWhite.setFont(font);
        styleWhite.setAlignment(HorizontalAlignment.CENTER);
        styleWhite.setVerticalAlignment(VerticalAlignment.CENTER);

        // Tạo style nền xám nhạt
        CellStyle styleGray = workbook.createCellStyle();
        styleGray.cloneStyleFrom(styleWhite);
        styleGray.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleGray.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        boolean isGray = false; // Bắt đầu với màu trắng

        for (Order order : orders) {
            if (order == null || order.getOrderDetails() == null || order.getOrderDetails().isEmpty()) {
                continue; // Bỏ qua hóa đơn không có sản phẩm
            }

            // Chọn style dựa trên màu hiện tại
            CellStyle currentStyle = isGray ? styleGray : styleWhite;

            int startRow = rowIdx;

            for (OrderDetail detail : order.getOrderDetails()) {
                if (detail == null || detail.getProductVariantDetail() == null || detail.getProductVariantDetail().getProduct() == null) {
                    continue; // Bỏ qua chi tiết bị thiếu dữ liệu
                }

                Row row = sheet.createRow(rowIdx++);

                createCell(row, 4, detail.getProductVariantDetail().getSKU(), currentStyle); // Mã SP
                createCell(row, 5, detail.getProductVariantDetail().getProduct().getName(), currentStyle); // Tên SP
                createCell(row, 6, detail.getQuantity(), currentStyle); // Số lượng
                createCell(row, 7, detail.getPrice(), currentStyle); // Đơn giá

                double thanhTien = detail.getQuantity() * detail.getPrice();
                createCell(row, 8, thanhTien, currentStyle); // Thành tiền
            }

            // Merge cells cho các cột chung của hóa đơn
            if (order.getOrderDetails().size() > 1) {
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 0, 0)); // Mã HĐ
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 1, 1)); // Ngày tạo
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 2, 2)); // Ngày hoàn thành
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 3, 3)); // Tên KH
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 9, 9)); // Voucher
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 10, 10)); // Tổng tiền
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 11, 11)); // Người nhận
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 12, 12)); // Địa chỉ
                sheet.addMergedRegion(new CellRangeAddress(startRow, rowIdx - 1, 13, 13)); // SĐT

                // Áp dụng style cho các vùng đã hợp nhất
            }

            // Ghi dữ liệu vào ô đầu tiên của vùng hợp nhất
            Row firstRow = sheet.getRow(startRow);
            if (firstRow == null) {
                firstRow = sheet.createRow(startRow);
            }

            createCell(firstRow, 0, order.getCode(), currentStyle); // Mã HĐ
            createCell(firstRow, 1, order.getCreatedAt() != null ? order.getCreatedAt().format(formatter) : "", currentStyle); // Ngày tạo
            createCell(firstRow, 2, order.getUpdatedAt() != null ? order.getUpdatedAt().format(formatter) : "", currentStyle); // Ngày hoàn thành
            createCell(firstRow, 3, order.getCustomer().getFullName(), currentStyle); // Tên KH
            createCell(firstRow, 9, order.getVoucher() != null ? order.getVoucher().getCode() : "Không", currentStyle); // Voucher
            createCell(firstRow, 10, order.getTotal(), currentStyle); // Tổng tiền
            createCell(firstRow, 11, order.getRecipientName(), currentStyle); // Người nhận
            createCell(firstRow, 12, order.getOrdersAddress(), currentStyle); // Địa chỉ
            createCell(firstRow, 13, order.getRecipientPhone(), currentStyle); // SĐT

            // Đổi màu nền cho đối tượng tiếp theo
            isGray = !isGray;
        }

        // Auto-size columns
        for (int i = 0; i < 14; i++) {
            sheet.autoSizeColumn(i);
        }
    }


    public void export(HttpServletResponse response) throws IOException {
        // Ghi tiêu đề và dữ liệu vào workbook
        writeHeader();
        writeDataLines();

        // Ghi workbook vào output stream của HTTP response
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } finally {
            workbook.close(); // Đóng workbook để giải phóng tài nguyên
        }
    }

}
