package com.example.backend.Library.component;

import com.example.backend.Library.model.entity.promotion.Promotion;
import com.example.backend.Library.repository.Promotion_Repository;
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
 * Component chịu trách nhiệm lập lịch và cập nhật trạng thái khuyến mãi dựa trên tính hợp lệ của chúng.
 * Trạng thái có thể bao gồm: Hoạt động, Không hoạt động, Sắp bắt đầu, Hết hạn.
 */
@Component
public class PromotionScheduler {

    // Logger để ghi lại các cập nhật và thay đổi trạng thái
    private static final Logger LOGGER = LoggerFactory.getLogger(PromotionScheduler.class);

    // Hằng số trạng thái cho các trạng thái khuyến mãi khác nhau
    private static final int ACTIVE_STATUS = 1;      // Khuyến mãi đang hoạt động
    private static final int INACTIVE_STATUS = 2;    // Khuyến mãi không hoạt động (do nhân viên vô hiệu hóa)
    private static final int UPCOMING_STATUS = 3;    // Khuyến mãi sắp bắt đầu
    private static final int EXPIRED_STATUS = 4;     // Khuyến mãi đã hết hạn (quá ngày kết thúc)

    private final Promotion_Repository promotionRepository;

    /**
     * Constructor để tiêm vào repository Promotion.
     *
     * @param promotionRepository Repository để truy cập dữ liệu khuyến mãi
     */
    @Autowired
    public PromotionScheduler(Promotion_Repository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    /**
     * Phương thức lập lịch để cập nhật trạng thái khuyến mãi ở một tỷ lệ cố định.
     * Khoảng thời gian có thể được cấu hình qua thuộc tính ứng dụng (mặc định là 1 phút).
     */
    @Scheduled(fixedRateString = "${promotion.update.interval:1000}")
    @Transactional
    public void updatePromotionStatuses() {
        LocalDateTime now = LocalDateTime.now(); // Lấy thời gian hiện tại
        List<Promotion> promotions = promotionRepository.findAll(); // Lấy tất cả các khuyến mãi

        // Cập nhật trạng thái cho các khuyến mãi cần thiết
        promotions.stream()
                .filter(promotion -> needsStatusUpdate(promotion, now)) // Lọc các khuyến mãi cần cập nhật trạng thái
                .forEach(promotion -> updatePromotionStatus(promotion, now)); // Cập nhật trạng thái cho từng khuyến mãi
    }

    /**
     * Kiểm tra xem trạng thái khuyến mãi có cần cập nhật hay không.
     *
     * @param promotion Khuyến mãi cần kiểm tra
     * @param now       Thời gian hiện tại
     * @return true nếu trạng thái khuyến mãi cần cập nhật, false nếu không
     */
    private boolean needsStatusUpdate(Promotion promotion, LocalDateTime now) {
        int newStatus = determinePromotionStatus(promotion, now); // Xác định trạng thái mới
        return promotion.getStatus() != newStatus; // Kiểm tra xem trạng thái hiện tại có khác với trạng thái mới hay không
    }

    /**
     * Cập nhật trạng thái của khuyến mãi và ghi lại thay đổi.
     *
     * @param promotion Khuyến mãi cần cập nhật
     * @param now       Thời gian hiện tại
     */
    private void updatePromotionStatus(Promotion promotion, LocalDateTime now) {
        int newStatus = determinePromotionStatus(promotion, now); // Xác định trạng thái mới
        promotion.setStatus(newStatus); // Cập nhật trạng thái cho khuyến mãi
        promotionRepository.save(promotion); // Lưu thay đổi vào cơ sở dữ liệu
        LOGGER.info("Đã cập nhật khuyến mãi: Mã = {}, Trạng thái mới = {}", promotion.getCode(), newStatus); // Ghi lại cập nhật
    }

    /**
     * Xác định trạng thái của khuyến mãi dựa trên ngày bắt đầu và ngày kết thúc.
     *
     * @param promotion Khuyến mãi cần đánh giá
     * @param now       Thời gian hiện tại
     * @return Trạng thái của khuyến mãi dưới dạng số nguyên
     */
    private int determinePromotionStatus(Promotion promotion, LocalDateTime now) {
        if (promotion.getStatus() == INACTIVE_STATUS) {
            return INACTIVE_STATUS; // Giữ nguyên trạng thái nếu đã vô hiệu hóa khuyến mãi
        }
        if (isExpired(promotion, now)) {
            return EXPIRED_STATUS; // Đánh dấu hết hạn nếu khuyến mãi đã quá ngày kết thúc
        }
        if (isActive(promotion, now)) {
            return ACTIVE_STATUS; // Trạng thái hoạt động nếu thỏa mãn các điều kiện
        } else if (isUpcoming(promotion, now)) {
            return UPCOMING_STATUS; // Trạng thái sắp bắt đầu nếu ngày bắt đầu ở tương lai
        } else {
            return INACTIVE_STATUS; // Trạng thái không hoạt động nếu không còn điều kiện nào khác
        }
    }

    /**
     * Kiểm tra xem khuyến mãi có đang hoạt động hay không.
     *
     * @param promotion Khuyến mãi cần kiểm tra
     * @param now       Thời gian hiện tại
     * @return true nếu khuyến mãi đang hoạt động, false nếu không
     */
    private boolean isActive(Promotion promotion, LocalDateTime now) {
        return Objects.nonNull(promotion.getStartDate()) &&
                !promotion.getStartDate().isAfter(now) && // Ngày bắt đầu phải là hôm nay hoặc trước đó
                (Objects.isNull(promotion.getEndDate()) || promotion.getEndDate().isAfter(now)); // Ngày kết thúc phải là trong tương lai hoặc chưa được đặt
    }

    /**
     * Kiểm tra xem khuyến mãi có sắp bắt đầu hay không.
     *
     * @param promotion Khuyến mãi cần kiểm tra
     * @param now       Thời gian hiện tại
     * @return true nếu khuyến mãi sắp bắt đầu, false nếu không
     */
    private boolean isUpcoming(Promotion promotion, LocalDateTime now) {
        return Objects.nonNull(promotion.getStartDate()) &&
                promotion.getStartDate().isAfter(now); // Ngày bắt đầu phải ở tương lai
    }

    /**
     * Kiểm tra xem khuyến mãi có hết hạn hay không.
     *
     * @param promotion Khuyến mãi cần kiểm tra
     * @param now       Thời gian hiện tại
     * @return true nếu khuyến mãi đã hết hạn, false nếu không
     */
    private boolean isExpired(Promotion promotion, LocalDateTime now) {
        return Objects.nonNull(promotion.getEndDate()) && promotion.getEndDate().isBefore(now); // Ngày kết thúc đã qua
    }
}
