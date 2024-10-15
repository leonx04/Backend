package com.example.backend.Library.service.impl;

import com.example.backend.Library.exception.DataNotFoundException;
import com.example.backend.Library.model.dto.request.LoginRequest;
import com.example.backend.Library.model.dto.request.RegisterRequest;
import com.example.backend.Library.model.dto.request.customer.CustomerRequest;
import com.example.backend.Library.model.dto.response.CustomerResponse;
import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.model.mapper.customer.CustomerMapper;
import com.example.backend.Library.repository.customer.CustomerRepository;
import com.example.backend.Library.service.interfaces.ICustomerService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class CustomerService implements ICustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder encoder;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, PasswordEncoder encoder) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.encoder = encoder;
    }

    // Tạo mới khách hàng
    @Override
    public Customer createCustomer(RegisterRequest request) {
        String username = request.getUserName();
        String email = request.getEmail();
        if (customerRepository.existsByUserName(username)) {
            throw new DataIntegrityViolationException("Username already exists");
        }
        if (customerRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email đã được sử dụng");
        }
        Customer customer = customerMapper.registerCustomer(request);
        customer.setCode(generateNextCode());
        customer.setPassword(encoder.encode(request.getPassword()));
        customer.setStatus(1);

        return customerRepository.save(customer);
    }

    // Lấy danh sách khách hàng
    @Override
    public Page<CustomerResponse> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerMapper::toCustomer);
    }

    // Lấy thông tin khách hàng dựa trên id
    @Override
    public Customer getCustomerById(int id) throws Exception {
        return customerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Customer not found with id: " + id));
    }

    // Lấy thông tin khách hàng dựa trên email
    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // Cập nhật thông tin khách hàng
    @Override
    public Customer updateCustomer(int id, CustomerRequest request) throws Exception {
        Customer customerOld = customerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Customer not found with id: " + id));
        Customer customerUpdate = customerMapper.updateCustomer(request);
        customerUpdate.setId(id);
        customerUpdate.setEmail(customerOld.getEmail());
        customerUpdate.setCode(customerOld.getCode());

        getOldValue(customerOld, request, customerUpdate);

        return customerRepository.save(customerUpdate);
    }

    // Khách hàng xoá tk vĩnh viễn
    @Override
    public void deleteCustomer(int id) throws Exception {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.delete(customer.get());
        } else {
            throw new DataNotFoundException("Customer not found with id: " + id);
        }
    }

    // Khách hàng xoá mềm
    @Override
    public void softDeleteCustomer(int id) throws Exception{
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Customer not found with id: " + id));
        customer.setStatus(2); // Đặt trạng thái là 2 (đã xóa)
        customerRepository.save(customer);
    }


    // Method cập nhật trạng thái của khách hàng
    // (cập nhật dựa trên giá trị từ combobox("Hoạt động", "Không hoạt động", "Đã xóa"))
    @Override
    public Customer updateCustomerStatus(int id, int status) throws Exception {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Customer not found with id: " + id));
        customer.setStatus(status); // Cập nhật trạng thái dựa trên giá trị truyền vào (0: Không hoạt động, 1: Hoạt động, 2: Đã xóa)
        return customerRepository.save(customer);
    }

    // Hàm kiểm tra xem khách hàng có tồn tại không
    @Override
    public boolean existsByEmail(String email) {
        System.out.println("Email: " + email);
        return customerRepository.existsByEmail(email);
    }

    // Hàm kiểm tra và lưu ảnh
//    @Override
//    public ResponseEntity<?> checkCreateImage(CustomerRequest request, MultipartFile avatar) {
//        if (avatar != null && !avatar.isEmpty()) {
//            // Kiểm tra kích thước file và định dạng
//            if(avatar.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
//                return ResponseEntity.badRequest()
//                        .body("Dung lượng ảnh vượt quá 10MB");
//            }
//            String contentType = avatar.getContentType();
//            if(contentType == null || !contentType.startsWith("image/")) {
//                return ResponseEntity.badRequest()
//                        .body("Chọn một file ảnh");
//            }
//
//            String fileName = null;
//            try {
//                fileName = storeFile(avatar);
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi lưu file ảnh");
//            }
//            request.setImageURL(fileName);
//        }
//        return null;
//    }

    // Hàm cập nhật ảnh
    @Override
    public void updateImage(int id, CustomerRequest request, MultipartFile avatar) throws IOException {
        // Lấy thông tin khách hàng hiện tại
        Customer customer = customerRepository.findById(id).orElse(null);

        // Kiểm tra và xóa ảnh cũ nếu có avatar mới
        if (avatar != null && !avatar.isEmpty()) {
            assert customer != null;
            String oldAvatar = customer.getImageURL();
            if (oldAvatar != null && !oldAvatar.isEmpty()) {
                deleteOldAvatar(oldAvatar);
            }
            // Lưu ảnh mới
            String newAvatarFilename = storeFile(avatar);
            request.setImageURL(newAvatarFilename);
        } else {
            // Nếu không có ảnh mới, giữ nguyên ảnh cũ
            assert customer != null;
            request.setImageURL(customer.getImageURL());
        }
    }

    // Hàm xác thực khách hàng
    @Override
    public boolean authenticateCustomer(LoginRequest loginRequest) {
        // Ghi log thông tin xác thực
        logger.info("Attempting to authenticate user: {}", loginRequest.getEmail());

        // Kiểm tra xem khách hàng có tồn tại không
        try {
            Optional<Customer> existsCustomer = customerRepository.findByEmail(loginRequest.getEmail());

            // Nếu không tìm thấy khách hàng
            if (existsCustomer.isEmpty()) {
                // Ghi log thông báo không tìm thấy khách hàng
                logger.warn("User not found: {}", loginRequest.getEmail());
                return false;
            }

            // Nếu tìm thấy khách hàng, kiểm tra mật khẩu
            Customer customer = existsCustomer.get();
            // So sánh mật khẩu đã mã hóa với mật khẩu người dùng nhập vào
            boolean passwordMatches = encoder.matches(loginRequest.getPassword(), customer.getPassword());

            // Nếu mật khẩu khớp
            if (passwordMatches) {
                // Ghi log thông báo xác thực thành công
                logger.info("Authentication successful for user: {}", loginRequest.getEmail());
                return true;
            } else {
                // Ghi log thông báo mật khẩu không khớp
                logger.warn("Password mismatch for user: {}", loginRequest.getEmail());
                return false;
            }
        } catch (Exception e) {
            // Ghi log thông báo lỗi xác thực
            logger.error("Error during authentication for user: {}", loginRequest.getEmail(), e);
            throw new RuntimeException("Lỗi trong quá trình xác thực: " + e.getMessage());
        }
    }


    /* Hàm tạo dữ liệu giả cho khách hàng */

    // Method tạo dữ liệu giả cho khách hàng
    @Override
    public void createFakeCustomers(int count) {
        Faker faker = new Faker(new Locale("vi-VN"));
        for (int i = 0; i < count; i++) {
            Customer customer = new Customer();
            customer.setCode(generateNextCode());
            customer.setUserName(faker.name().username());
            customer.setFullName(faker.name().fullName());
            customer.setEmail(faker.internet().emailAddress());
            customer.setPassword(encoder.encode("123456"));
            customer.setPhone(faker.phoneNumber().cellPhone());
            customer.setGender(faker.number().numberBetween(0, 2));
            customer.setBirthDate(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            customer.setStatus(faker.number().numberBetween(0, 3));
            customerRepository.save(customer);
        }
    }

    // Method tìm kiếm khách hàng theo các thuộc tính của khách hàng
    @Override
    public Page<CustomerResponse> searchCustomers(String fullName, String email, String phone, Integer gender, Integer status, Pageable pageable) {
        // Tìm kiếm khách hàng theo các thuộc tính của khách hàng
//        return customerRepository.findByFullNameContainingAndEmailContainingAndPhoneContainingAndGenderContainingAndStatusContaining(
//                fullName, email, phone, gender, status, pageable)
//                .map(customerMapper::toCustomer);
        return customerRepository.searchCustomers(fullName, email, phone, gender, status, pageable)
                .map(customerMapper::toCustomer);
    }

    /* Các hàm hỗ trợ */


    // Hàm nhận giá trị cũ nếu không có giá trị mới
    private void getOldValue(Customer oldValua, CustomerRequest newValue, Customer customerUpdate) {
        if (newValue.getUserName() == null) {
            customerUpdate.setUserName(oldValua.getUserName());
        }
        if (newValue.getPassword() == null) {
            customerUpdate.setPassword(oldValua.getPassword());
        }
        if (newValue.getFullName() == null) {
            customerUpdate.setFullName(oldValua.getFullName());
        }
        if (newValue.getGender() == null) {
            customerUpdate.setGender(oldValua.getGender());
        }
        if (newValue.getPhone() == null) {
            customerUpdate.setPhone(oldValua.getPhone());
        }
        if (newValue.getBirthDate() == null) {
            customerUpdate.setBirthDate(oldValua.getBirthDate());
        }
        if (newValue.getStatus() == null) {
            customerUpdate.setStatus(oldValua.getStatus());
        }
    }

    // Hàm lưu file
    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // Đường dẫn đến thư mục mà bạn muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    // Hàm kiểm tra xem file có phải là ảnh không
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    // Hàm xóa ảnh cũ
    private void deleteOldAvatar(String filename) throws IOException {
        Path filePath = Paths.get("uploads", filename);
        Files.deleteIfExists(filePath);
    }

    // Hàm để sinh mã code tiếp theo
    private String generateNextCode() {
        Customer customerLastCode = customerRepository.findFirstByOrderByCodeDesc();

        String lastCode;
        if (customerLastCode != null) {
            lastCode = customerLastCode.getCode(); // Lấy mã code lớn nhất hiện tại
        } else {
            lastCode = "CUS0000000"; // Nếu chưa có sản phẩm nào, bắt đầu từ CUS0000000
        }

        // Tách phần tiền tố "CUS" và phần số từ mã code hiện tại
        String prefix = "CUS";
        String numberPart = lastCode.substring(3); // Lấy phần số (sau "CUS")

        // Chuyển phần số thành integer và tăng giá trị
        int number = Integer.parseInt(numberPart);

        // Format lại phần số với độ dài 7 chữ số
        return String.format("%s%07d", prefix, number + 1);
    }

}
