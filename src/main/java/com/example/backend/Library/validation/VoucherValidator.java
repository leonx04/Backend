package com.example.backend.Library.validation;

import com.example.backend.Library.model.dto.Voucher_Admin_DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class VoucherValidator {

    // Các hằng số xác định giới hạn độ dài và các giá trị tối đa
    private static final int MAX_CODE_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final BigDecimal MAX_PERCENTAGE = new BigDecimal("100");
    private static final Pattern CODE_PATTERN = Pattern.compile("^[A-Z0-9_-]{3,50}$");

    // Phương thức xác thực voucher
    public static List<String> validateVoucher(Voucher_Admin_DTO voucherDto) {
        List<String> errors = new ArrayList<>();

        // Gọi các phương thức xác thực riêng biệt và thu thập lỗi
        errors.addAll(validateCode(voucherDto.getCode())); // Xác thực mã voucher
        errors.addAll(validateDescription(voucherDto.getDescription())); // Xác thực mô tả
        errors.addAll(validateDates(voucherDto.getStartDate(), voucherDto.getEndDate())); // Xác thực ngày bắt đầu và kết thúc
        errors.addAll(validateDiscountValue(voucherDto.getDiscountValue(), voucherDto.getVoucherType())); // Xác thực giá trị giảm giá
        errors.addAll(validateMinimumOrderValue(voucherDto.getMinimumOrderValue())); // Xác thực giá trị đơn hàng tối thiểu
        errors.addAll(validateVoucherType(voucherDto.getVoucherType())); // Xác thực loại voucher
        errors.addAll(validateCustomerLimit(voucherDto.getCustomerLimit(), voucherDto.getQuantity())); // Xác thực giới hạn khách hàng
        errors.addAll(validateMaximumDiscountAmount(voucherDto.getMaximumDiscountAmount(), voucherDto.getVoucherType(), voucherDto.getDiscountValue())); // Xác thực số tiền giảm giá tối đa
        errors.addAll(validateQuantity(voucherDto.getQuantity())); // Xác thực số lượng
        errors.addAll(validateStatus(voucherDto.getStatus())); // Xác thực trạng thái

        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực mã voucher
    private static List<String> validateCode(String code) {
        List<String> errors = new ArrayList<>();
        if (code == null || code.trim().isEmpty()) {
            errors.add("Mã voucher là bắt buộc"); // Lỗi: mã voucher không được để trống
        } else if (code.length() > MAX_CODE_LENGTH) {
            errors.add("Mã voucher không được vượt quá " + MAX_CODE_LENGTH + " ký tự"); // Lỗi: quá độ dài tối đa
        } else if (!CODE_PATTERN.matcher(code).matches()) {
            errors.add("Mã voucher phải chỉ chứa chữ hoa, số, dấu gạch dưới và dấu gạch ngang, và phải từ 3 đến 50 ký tự"); // Lỗi: định dạng không hợp lệ
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực mô tả voucher
    private static List<String> validateDescription(String description) {
        List<String> errors = new ArrayList<>();
        if (description == null || description.trim().isEmpty()) {
            errors.add("Mô tả là bắt buộc"); // Lỗi: mô tả không được để trống
        } else if (description.length() > MAX_DESCRIPTION_LENGTH) {
            errors.add("Mô tả không được vượt quá " + MAX_DESCRIPTION_LENGTH + " ký tự"); // Lỗi: quá độ dài tối đa
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực ngày bắt đầu và ngày kết thúc
    private static List<String> validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        List<String> errors = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now(); // Lấy thời gian hiện tại

        if (startDate == null) {
            errors.add("Ngày bắt đầu là bắt buộc"); // Lỗi: ngày bắt đầu không được để trống
        } else if (startDate.isBefore(now)) {
            errors.add("Ngày bắt đầu phải ở tương lai"); // Lỗi: ngày bắt đầu không được trong quá khứ
        }

        if (endDate == null) {
            errors.add("Ngày kết thúc là bắt buộc"); // Lỗi: ngày kết thúc không được để trống
        } else if (endDate.isBefore(now)) {
            errors.add("Ngày kết thúc phải ở tương lai"); // Lỗi: ngày kết thúc không được trong quá khứ
        }

        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                errors.add("Ngày bắt đầu phải trước ngày kết thúc"); // Lỗi: ngày bắt đầu không được sau ngày kết thúc
            }
            if (startDate.plusMonths(6).isBefore(endDate)) {
                errors.add("Thời gian voucher không được vượt quá 6 tháng"); // Lỗi: thời gian voucher vượt quá 6 tháng
            }
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực giá trị giảm giá
    private static List<String> validateDiscountValue(BigDecimal discountValue, String voucherType) {
        List<String> errors = new ArrayList<>();
        if (discountValue == null) {
            errors.add("Giá trị giảm giá là bắt buộc"); // Lỗi: giá trị giảm giá không được để trống
        } else if (discountValue.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Giá trị giảm giá phải lớn hơn không"); // Lỗi: giá trị giảm giá không được nhỏ hơn hoặc bằng 0
        } else if ("PERCENTAGE".equals(voucherType) && discountValue.compareTo(MAX_PERCENTAGE) > 0) {
            errors.add("Giảm giá theo phần trăm không được vượt quá 100%"); // Lỗi: giảm giá phần trăm vượt quá 100%
        } else if ("FIXED_AMOUNT".equals(voucherType) && discountValue.scale() > 2) {
            errors.add("Giảm giá theo số tiền không được có quá 2 chữ số thập phân"); // Lỗi: số tiền giảm giá có quá nhiều chữ số thập phân
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực giá trị đơn hàng tối thiểu
    private static List<String> validateMinimumOrderValue(BigDecimal minimumOrderValue) {
        List<String> errors = new ArrayList<>();
        if (minimumOrderValue == null) {
            errors.add("Giá trị đơn hàng tối thiểu là bắt buộc"); // Lỗi: giá trị đơn hàng tối thiểu không được để trống
        } else if (minimumOrderValue.compareTo(BigDecimal.ZERO) < 0) {
            errors.add("Giá trị đơn hàng tối thiểu không được âm"); // Lỗi: giá trị đơn hàng tối thiểu không được âm
        } else if (minimumOrderValue.scale() > 2) {
            errors.add("Giá trị đơn hàng tối thiểu không được có quá 2 chữ số thập phân"); // Lỗi: số chữ số thập phân không hợp lệ
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực loại voucher
    private static List<String> validateVoucherType(String voucherType) {
        List<String> errors = new ArrayList<>();
        if (voucherType == null || voucherType.trim().isEmpty()) {
            errors.add("Loại voucher là bắt buộc"); // Lỗi: loại voucher không được để trống
        } else if (!voucherType.equals("percentage") && !voucherType.equals("fixed")) {
            errors.add("Loại voucher không hợp lệ. Phải là percentage hoặc fixed"); // Lỗi: loại voucher không hợp lệ
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực giới hạn khách hàng
    private static List<String> validateCustomerLimit(Integer customerLimit, Integer quantity) {
        List<String> errors = new ArrayList<>();
        if (customerLimit == null) {
            errors.add("Giới hạn khách hàng là bắt buộc"); // Lỗi: giới hạn khách hàng không được để trống
        } else if (customerLimit < 0) {
            errors.add("Giới hạn khách hàng không được âm"); // Lỗi: giới hạn khách hàng không được âm
        } else if (quantity != null && customerLimit > quantity) {
            errors.add("Giới hạn khách hàng không được vượt quá tổng số lượng voucher"); // Lỗi: giới hạn khách hàng vượt quá tổng số lượng voucher
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực số tiền giảm giá tối đa
    private static List<String> validateMaximumDiscountAmount(BigDecimal maximumDiscountAmount, String voucherType, BigDecimal discountValue) {
        List<String> errors = new ArrayList<>();
        if (maximumDiscountAmount == null) {
            errors.add("Số tiền giảm giá tối đa là bắt buộc"); // Lỗi: số tiền giảm giá tối đa không được để trống
        } else if (maximumDiscountAmount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("Số tiền giảm giá tối đa phải lớn hơn không"); // Lỗi: số tiền giảm giá tối đa không được nhỏ hơn hoặc bằng 0
        } else if (maximumDiscountAmount.scale() > 2) {
            errors.add("Số tiền giảm giá tối đa không được có quá 2 chữ số thập phân"); // Lỗi: số chữ số thập phân không hợp lệ
        } else if ("FIXED_AMOUNT".equals(voucherType) && discountValue != null && maximumDiscountAmount.compareTo(discountValue) < 0) {
            errors.add("Số tiền giảm giá tối đa phải lớn hơn hoặc bằng giá trị giảm giá cho voucher theo số tiền"); // Lỗi: số tiền giảm giá tối đa không hợp lệ
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực số lượng
    private static List<String> validateQuantity(Integer quantity) {
        List<String> errors = new ArrayList<>();
        if (quantity == null) {
            errors.add("Số lượng là bắt buộc"); // Lỗi: số lượng không được để trống
        } else if (quantity <= 0) {
            errors.add("Số lượng phải lớn hơn không"); // Lỗi: số lượng không được nhỏ hơn hoặc bằng 0
        } else if (quantity > 1000000) {
            errors.add("Số lượng không được vượt quá 1.000.000"); // Lỗi: số lượng vượt quá giới hạn
        }
        return errors; // Trả về danh sách lỗi
    }

    // Phương thức xác thực trạng thái
    private static List<String> validateStatus(Integer status) {
        List<String> errors = new ArrayList<>();
        if (status == null) {
            errors.add("Trạng thái là bắt buộc"); // Lỗi: trạng thái không được để trống
        } else if (status < 0 || status > 1) {
            errors.add("Trạng thái không hợp lệ. Phải là 0 (không hoạt động) hoặc 1 (hoạt động)"); // Lỗi: trạng thái không hợp lệ
        }
        return errors; // Trả về danh sách lỗi
    }
}
