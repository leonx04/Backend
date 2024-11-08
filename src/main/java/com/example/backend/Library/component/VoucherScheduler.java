package com.example.backend.Library.component;

import com.example.backend.Library.model.entity.voucher.Voucher;
import com.example.backend.Library.repository.voucher.Voucher_Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Component chịu trách nhiệm lập lịch và cập nhật trạng thái voucher dựa trên tính hợp lệ của chúng.
 * Trạng thái có thể bao gồm: Hoạt động, Không hoạt động, Sắp bắt đầu, Hết hạn, hoặc Đã hết hàng.
 */
@Component
public class VoucherScheduler {

    // Logger để ghi lại các cập nhật và thay đổi trạng thái
    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherScheduler.class);

    // Hằng số trạng thái cho các trạng thái voucher khác nhau
    private static final int ACTIVE_STATUS = 1;      // Voucher đang hoạt động
    private static final int INACTIVE_STATUS = 2;    // Voucher không hoạt động (do nhân viên vô hiệu hóa)
    private static final int UPCOMING_STATUS = 3;    // Voucher sắp bắt đầu
    private static final int SOLD_OUT_STATUS = 0;    // Voucher đã hết hàng
    private static final int EXPIRED_STATUS = 4;     // Voucher đã hết hạn (quá ngày kết thúc)

    private final Voucher_Repository voucherRepository;

    /**
     * Constructor để tiêm vào repository Voucher.
     *
     * @param voucherRepository Repository để truy cập dữ liệu voucher
     */
    @Autowired
    public VoucherScheduler(Voucher_Repository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /**
     * Phương thức lập lịch để cập nhật trạng thái voucher ở một tỷ lệ cố định.
     * Khoảng thời gian có thể được cấu hình qua thuộc tính ứng dụng (mặc định là 1 giây).
     */
    @Scheduled(fixedRateString = "${voucher.update.interval:1000}")
    @Transactional
    public void updateVoucherStatuses() {
        LocalDateTime now = LocalDateTime.now(); // Lấy thời gian hiện tại
        List<Voucher> vouchers = voucherRepository.findAll(); // Lấy tất cả các voucher

        // Cập nhật trạng thái cho các voucher cần thiết
        vouchers.stream()
                .filter(voucher -> needsStatusUpdate(voucher, now)) // Lọc các voucher cần cập nhật trạng thái
                .forEach(voucher -> updateVoucherStatus(voucher, now)); // Cập nhật trạng thái cho từng voucher
    }

    /**
     * Kiểm tra xem trạng thái voucher có cần cập nhật hay không.
     *
     * @param voucher Voucher cần kiểm tra
     * @param now     Thời gian hiện tại
     * @return true nếu trạng thái voucher cần cập nhật, false nếu không
     */
    private boolean needsStatusUpdate(Voucher voucher, LocalDateTime now) {
        int newStatus = determineVoucherStatus(voucher, now); // Xác định trạng thái mới
        return voucher.getStatus() != newStatus; // Kiểm tra xem trạng thái hiện tại có khác với trạng thái mới hay không
    }

    /**
     * Cập nhật trạng thái của voucher và ghi lại thay đổi.
     *
     * @param voucher Voucher cần cập nhật
     * @param now     Thời gian hiện tại
     */
    private void updateVoucherStatus(Voucher voucher, LocalDateTime now) {
        int oldStatus = voucher.getStatus(); // Lấy trạng thái hiện tại (trạng thái cũ)
        int newStatus = determineVoucherStatus(voucher, now); // Xác định trạng thái mới

        if (oldStatus != newStatus) { // Chỉ cập nhật nếu trạng thái thay đổi
            voucher.setStatus(newStatus); // Cập nhật trạng thái cho voucher
            voucherRepository.save(voucher); // Lưu thay đổi vào cơ sở dữ liệu
            LOGGER.info("Voucher cập nhật: Mã = {}, Trạng thái cũ = {}, Trạng thái mới = {}",
                    voucher.getCode(), oldStatus, newStatus); // Ghi lại thông tin vào log
        }
    }


    /**
     * Xác định trạng thái của voucher dựa trên ngày bắt đầu, ngày kết thúc và số lượng.
     *
     * @param voucher Voucher cần đánh giá
     * @param now     Thời gian hiện tại
     * @return Trạng thái của voucher dưới dạng số nguyên
     */
    private int determineVoucherStatus(Voucher voucher, LocalDateTime now) {
        if (voucher.getStatus() == INACTIVE_STATUS) {
            return INACTIVE_STATUS; // Giữ nguyên trạng thái nếu đã vô hiệu hóa voucher
        }
        if (isExpired(voucher, now)) {
            return EXPIRED_STATUS; // Đánh dấu hết hạn nếu voucher đã quá ngày kết thúc
        }
        if (isActive(voucher, now)) {
            return ACTIVE_STATUS; // Trạng thái hoạt động nếu thỏa mãn các điều kiện
        } else if (isUpcoming(voucher, now)) {
            return UPCOMING_STATUS; // Trạng thái sắp bắt đầu nếu ngày bắt đầu ở tương lai
        } else if (isInactive(voucher, now)) {
            return INACTIVE_STATUS; // Trạng thái không hoạt động nếu voucher hết hàng hoặc quá ngày kết thúc
        } else {
            return SOLD_OUT_STATUS; // Trạng thái hết hàng nếu số lượng voucher bằng 0
        }
    }

    /**
     * Kiểm tra xem voucher có đang hoạt động hay không.
     *
     * @param voucher Voucher cần kiểm tra
     * @param now     Thời gian hiện tại
     * @return true nếu voucher đang hoạt động, false nếu không
     */
    private boolean isActive(Voucher voucher, LocalDateTime now) {
        return Objects.nonNull(voucher.getStartDate()) &&
                now.isAfter(voucher.getStartDate()) &&  // Thời gian hiện tại sau ngày bắt đầu
                now.isBefore(voucher.getEndDate()) &&   // Thời gian hiện tại trước ngày kết thúc
                voucher.getQuantity() > 0;              // Còn số lượng
    }

    /**
     * Kiểm tra xem voucher có sắp bắt đầu hay không.
     *
     * @param voucher Voucher cần kiểm tra
     * @param now     Thời gian hiện tại
     * @return true nếu voucher sắp bắt đầu, false nếu không
     */
    private boolean isUpcoming(Voucher voucher, LocalDateTime now) {
        return Objects.nonNull(voucher.getStartDate()) &&
                voucher.getStartDate().isAfter(now); // Ngày bắt đầu phải ở tương lai
    }

    /**
     * Kiểm tra xem voucher có không hoạt động hay không.
     *
     * @param voucher Voucher cần kiểm tra
     * @param now     Thời gian hiện tại
     * @return true nếu voucher không hoạt động, false nếu không
     */
    private boolean isInactive(Voucher voucher, LocalDateTime now) {
        return (Objects.nonNull(voucher.getEndDate()) && voucher.getEndDate().isBefore(now)) || // Ngày kết thúc phải ở quá khứ
                voucher.getQuantity() == 0; // Voucher phải không còn số lượng khả dụng
    }

    /**
     * Kiểm tra xem voucher có hết hạn hay không.
     *
     * @param voucher Voucher cần kiểm tra
     * @param now     Thời gian hiện tại
     * @return true nếu voucher đã hết hạn, false nếu không
     */
    private boolean isExpired(Voucher voucher, LocalDateTime now) {
        return Objects.nonNull(voucher.getEndDate()) && voucher.getEndDate().isBefore(now); // Ngày kết thúc đã qua
    }
}
