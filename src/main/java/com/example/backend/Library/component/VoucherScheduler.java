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
    @Scheduled(fixedRate = 60000) // 60000 milliseconds = 1 minute
    public void updateExpiredVouchers() {
        LocalDateTime now = LocalDateTime.now();

        // Lấy danh sách voucher có ngày kết thúc nhỏ hơn ngày hiện tại
        List<Voucher> expiredVouchers = voucherRepository.findByEndDateBefore(now);

        // Cập nhật trạng thái của các voucher đã hết hạn
        for (Voucher voucher : expiredVouchers) {
            voucher.setStatus(2); // Đặt trạng thái thành 2
            voucherRepository.save(voucher); // Lưu thay đổi vào cơ sở dữ liệu
        }
    }
}