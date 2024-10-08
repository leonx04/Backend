package com.example.backend.Library.component;

import com.example.backend.Library.model.entity.Voucher;
import com.example.backend.Library.repository.Voucher_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
/**
 * VoucherScheduler chịu trách nhiệm tự động cập nhật trạng thái của các voucher
 * dựa trên các tiêu chí như ngày hết hạn, ngày bắt đầu, và số lượng.
 * Bộ lập lịch này chạy định kỳ để đảm bảo trạng thái của voucher luôn được cập nhật.
 */
@Component
public class VoucherScheduler {

    @Autowired
    private Voucher_Repository voucherRepository;

    /**
     * Cập nhật trạng thái voucher mỗi giây.
     * Phương thức này là điểm khởi đầu cho tất cả các hoạt động cập nhật trạng thái voucher.
     */
    @Scheduled(fixedRate = 1000) // Chạy mỗi giây
    public void updateVoucherStatuses() {
        LocalDateTime now = LocalDateTime.now(); // Lấy thời gian hiện tại
        LocalDate today = LocalDate.now(); // Lấy ngày hiện tại

        updateExpiredVouchers(now); // Cập nhật các voucher đã hết hạn
        updateFutureVouchers(now); // Cập nhật các voucher sắp có hiệu lực
        updateStartingVouchers(today); // Cập nhật các voucher bắt đầu hôm nay
        updateZeroQuantityVouchers(); // Cập nhật các voucher có số lượng bằng 0
    }

    /**
     * Cập nhật trạng thái của các voucher đã hết hạn.
     *
     * @param now Thời gian hiện tại
     */
    private void updateExpiredVouchers(LocalDateTime now) {
        // Tìm các voucher đã kết thúc trước thời gian hiện tại
        List<Voucher> expiredVouchers = voucherRepository.findByEndDateBefore(now);
        for (Voucher voucher : expiredVouchers) {
            // Cập nhật trạng thái thành 2 (hết hạn) nếu chưa phải
            if (voucher.getStatus() != 2) {
                voucher.setStatus(2);
                voucherRepository.save(voucher);
            }
        }
    }

    /**
     * Cập nhật trạng thái của các voucher chưa bắt đầu.
     *
     * @param now Thời gian hiện tại
     */
    private void updateFutureVouchers(LocalDateTime now) {
        // Tìm các voucher có ngày bắt đầu sau thời gian hiện tại
        List<Voucher> futureVouchers = voucherRepository.findByStartDateAfter(now);
        for (Voucher voucher : futureVouchers) {
            // Cập nhật trạng thái thành 3 (sắp có hiệu lực) nếu chưa phải
            if (voucher.getStatus() != 3) {
                voucher.setStatus(3);
                voucherRepository.save(voucher);
            }
        }
    }

    /**
     * Cập nhật trạng thái của các voucher bắt đầu từ hôm nay.
     *
     * @param today Ngày hiện tại
     */
    private void updateStartingVouchers(LocalDate today) {
        // Tìm các voucher bắt đầu vào đầu ngày hôm nay
        List<Voucher> startingVouchers = voucherRepository.findByStartDateEquals(today.atStartOfDay());
        for (Voucher voucher : startingVouchers) {
            // Cập nhật trạng thái thành 1 (hoạt động) nếu chưa phải
            if (voucher.getStatus() != 1) {
                voucher.setStatus(1);
                voucherRepository.save(voucher);
            }
        }
    }

    /**
     * Cập nhật trạng thái của các voucher có số lượng bằng 0.
     * Phương thức này được gọi mỗi giây để nhanh chóng cập nhật các voucher hết hàng.
     */
    private void updateZeroQuantityVouchers() {
        // Tìm các voucher có số lượng bằng 0 và trạng thái không phải 2 (hết hạn)
        List<Voucher> zeroQuantityVouchers = voucherRepository.findByQuantityAndStatusNot(0, 2);
        for (Voucher voucher : zeroQuantityVouchers) {
            // Cập nhật trạng thái thành 2 (hết hạn hoặc không khả dụng)
            voucher.setStatus(2);
            voucherRepository.save(voucher);
        }
    }
}
