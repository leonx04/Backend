package com.example.backend.Library.component;

import com.example.backend.Library.model.entity.Voucher;
import com.example.backend.Library.repository.Voucher_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class VoucherScheduler {

    @Autowired
    private Voucher_Repository voucherRepository;

    // Phương thức sẽ được gọi mỗi phút
    @Scheduled(fixedRate = 6000) // 6000 milliseconds = 1 second
    public void updateVoucherStatuses() {
        LocalDateTime now = LocalDateTime.now();

        // Cập nhật voucher đã hết hạn
        updateExpiredVouchers(now);

        // Cập nhật voucher chưa đến ngày bắt đầu
        updateFutureVouchers(now);
    }

    private void updateExpiredVouchers(LocalDateTime now) {
        // Lấy danh sách voucher có ngày kết thúc nhỏ hơn ngày hiện tại
        List<Voucher> expiredVouchers = voucherRepository.findByEndDateBefore(now);

        // Cập nhật trạng thái của các voucher đã hết hạn
        for (Voucher voucher : expiredVouchers) {
            if (voucher.getStatus() != 2) {
                voucher.setStatus(2); // Đặt trạng thái thành 2
                voucherRepository.save(voucher); // Lưu thay đổi vào cơ sở dữ liệu
            }
        }
    }

    private void updateFutureVouchers(LocalDateTime now) {
        // Lấy danh sách voucher có ngày bắt đầu lớn hơn ngày hiện tại
        List<Voucher> futureVouchers = voucherRepository.findByStartDateAfter(now);

        // Cập nhật trạng thái của các voucher chưa đến ngày bắt đầu
        for (Voucher voucher : futureVouchers) {
            if (voucher.getStatus() != 3) {
                voucher.setStatus(3); // Đặt trạng thái thành 2
                voucherRepository.save(voucher); // Lưu thay đổi vào cơ sở dữ liệu
            }
        }
    }
}