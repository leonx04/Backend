package com.example.backend.Library.exception;

import com.example.backend.Library.exception.exceptioncustomer.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Được ném ra khi validation thất bại cho các tham số phương thức được đánh dấu bằng @Valid
     * Thường sử dụng với các đối tượng DTO (Data Transfer Objects)
     * Áp dụng cho các method parameters trong Controller : Validate nguyên đối tượng
     * Vd : @PostMapping(@Valid @RequestBody UserDto userDto)
     *
     * @param ex Ngoại lệ MethodArgumentNotValidException chứa thông tin về lỗi xác thực (Validation Error).
     * @param request Đối tượng WebRequest cung cấp thông tin về yêu cầu HTTP hiện tại.
     * @return Một đối tượng ErrorResponse chứa thông tin lỗi được tạo ra từ ngoại lệ.
     *
     *  * - **@ExceptionHandler({MethodArgumentNotValidException.class})**:
     *   Đây là một annotation dùng để chỉ định rằng phương thức sẽ xử lý các ngoại lệ kiểu `MethodArgumentNotValidException`.
     *   Annotation này cho phép Spring tự động gọi phương thức này khi gặp phải loại ngoại lệ này.
     *
     * - **@ResponseStatus(HttpStatus.BAD_REQUEST)**:
     *   Annotation này chỉ định rằng khi phương thức xử lý lỗi được gọi, mã trạng thái HTTP trả về sẽ là **400 (Bad Request)**.
     *   Điều này có nghĩa là yêu cầu của người dùng đã gặp phải lỗi và dữ liệu đầu vào không hợp lệ, vì vậy, mã trạng thái 400 sẽ được trả về để người dùng biết yêu cầu của họ không hợp lệ.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        log.info("============== handleValidationException ==============");
        String errorMessage = ExceptionMessageFormatter.formatValidationMessage(ex);
        ErrorResponse errorResponse = createErrorResponse(ex, request);
        errorResponse.setMessage(errorMessage);
        return errorResponse;
    }


    /**
     * Được ném ra khi validation thất bại cho các tham số phương thức được đánh dấu bằng @Validated
     * Thường sử dụng với việc validation các tham số đầu vào phương thức (method parameters)
     * Phạm Vi : Có thể sử dụng ở Service, Component
     * Cách Áp Dụng : Validate từng tham số
     * Vd:
     * @Validated
     * public class UserService {
     *     public void createUser(
     *         @NotNull(message = "ID không được null") Long id,
     *         @Email(message = "Email không hợp lệ") String email,
     *         @Size(min = 2, max = 50, message = "Tên phải từ 2-50 ký tự") String name
     *     ){
     *          // Nếu bất kỳ tham số nào vi phạm ràng buộc,
     *         // ConstraintViolationException sẽ được ném ra};
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        log.info("============== handleConstraintViolationException ==============");
        String errorMessage = ExceptionMessageFormatter.formatConstraintViolationMessage(ex);

        ErrorResponse errorResponse = createErrorResponse(ex, request);
        errorResponse.setMessage(errorMessage);

        return errorResponse;
    }

    /**
     * Khi tham số yêu cầu trong yêu cầu HTTP bị thiếu.
     * Các tham số liên quan : Tham số yêu cầu trong query string, URL path, hoặc form data.
     *Lý do ném ngoại lệ	Tham số bắt buộc không có trong yêu cầu từ client
     * Vd:
     * Thiếu tham số trong query string, ví dụ: GET /products/search?name=Apple.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        log.info("============== handleMissingServletRequestParameterException ==============");
        return createErrorResponse(ex, request);
    }


    /**
     * Được ném ra khi không tìm thấy tài nguyên theo yêu cầu của client.
     * Thường được sử dụng trong các trường hợp như tìm kiếm một đối tượng trong cơ sở dữ liệu
     * và không có đối tượng nào khớp với thông tin tìm kiếm.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.info("============== handleResourceNotFoundException ==============");
        return createErrorResponse(ex, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex, WebRequest request) {
        log.info("============== handleException ==============");
        return createErrorResponse(ex, request);
    }


    //HttpMessageNotReadableException
    //InternalAuthenticationServiceException

    private ErrorResponse createErrorResponse(Exception ex, WebRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getDescription(false).replace("uri=", ""))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .build();
    }
}
