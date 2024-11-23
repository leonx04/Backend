package com.example.backend.Library.service.impl.customer;

import com.example.backend.Library.exception.DataNotFoundException;
import com.example.backend.Library.model.dto.request.auth.LoginRequest;
import com.example.backend.Library.model.dto.request.auth.RegisterRequest;
import com.example.backend.Library.model.dto.request.customer.CustomerRequest;
import com.example.backend.Library.model.dto.response.customer.CustomerResponse;
import com.example.backend.Library.model.entity.cart.*;
import com.example.backend.Library.model.entity.orders.*;
import com.example.backend.Library.model.entity.customer.*;
import com.example.backend.Library.model.mapper.customer.CustomerMapper;
import com.example.backend.Library.repository.cart.CartDetailRepository;
import com.example.backend.Library.repository.cart.CartRepository;
import com.example.backend.Library.repository.customer.*;
import com.example.backend.Library.repository.orders.*;
import com.example.backend.Library.service.interfaces.customer.ICustomerService;
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
import java.nio.file.*;
import java.text.Normalizer;
import java.time.ZoneId;
import java.util.*;

@Service
public class CustomerService implements ICustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder encoder;

    public CustomerService(CustomerRepository customerRepository, CartDetailRepository cartDetailRepository, OrderDetailRepository orderDetailRepository, CustomerMapper customerMapper, PasswordEncoder encoder, OrderRepository orderRepository, CartRepository cartRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.customerMapper = customerMapper;
        this.encoder = encoder;
        this.addressRepository = addressRepository;
    }

    // Tạo mới khách hàng
    @Override
    public Customer registerCustomer(RegisterRequest request) {
        String username = request.getUserName();
        String email = request.getEmail();
        if (customerRepository.existsByUserName(username)) {
            throw new DataIntegrityViolationException("Tên đăng nhập đã được sử dụng");
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

    private String fullnameToUsername (String fullname) {
        // Chuyển fullname về chữ thường
        String lowerCaseString = fullname.toLowerCase();
        System.out.println("Lowercase: " + lowerCaseString);

        // Loại bỏ dấu
        String normalizedString = Normalizer.normalize(lowerCaseString, Normalizer.Form.NFD);
        String withoutDiacritics = normalizedString.replaceAll("\\p{M}", "");
        System.out.println("Without diacritics: " + withoutDiacritics);

        // Loại bỏ khoảng trắng
        return withoutDiacritics.replaceAll("\\s+", "");
    }

    @Override
    public Customer createCustomer(CustomerRequest request) {
        String email = request.getEmail();
        if (customerRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email đã được sử dụng");
        }
        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new DataIntegrityViolationException("Số điện thoại đã được sử dụng");
        }
        Customer customer = customerMapper.toCustomerRequest(request);
        if (request.getUserName() == null || request.getUserName().isEmpty()) {
            String username = fullnameToUsername(request.getFullName());
            int suffix = 1;
            while (customerRepository.existsByUserName(username)) {
                username = fullnameToUsername(request.getFullName()) + suffix;
                suffix++;
            }
            customer.setUserName(username);
        }
        customer.setCode(generateNextCode());
        customer.setPassword(encoder.encode("12345678"));
        customer.setStatus(1);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateAdminCustomer(int id, CustomerRequest request) throws Exception {
        Customer customerOld = customerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Customer not found with id: " + id));
        Customer customerUpdate = customerMapper.updateCustomer(request);
        customerUpdate.setId(id);
        customerUpdate.setEmail(customerOld.getEmail());
        customerUpdate.setCode(customerOld.getCode());
        customerUpdate.setImageURL(customerOld.getImageURL());

        getOldValue(customerOld, request, customerUpdate);

        return customerRepository.save(customerUpdate);
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
    public Optional<Customer> findByEmail(String email) {
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
    public void deleteCustomer(int id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        try {
            if (customerOpt.isPresent()) {
                Customer customer = customerOpt.get();
                List<Address> addresses = addressRepository.findAllByCustomer(customer);
                addressRepository.deleteAll(addresses);
                Cart cart = cartRepository.findByUserId(id);
                if (cart != null) {
                    List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cart.getId());
                    cartDetailRepository.deleteAll(cartDetails);
                    cartRepository.delete(cart);
                }
                List<Order> orders = orderRepository.findByCustomerId(id);
                if (orders != null) {
                    for (Order order : orders) {
                        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
                        orderDetailRepository.deleteAll(orderDetails);
                    }
                    orderRepository.deleteAll(orders);
                }
                customerRepository.delete(customer);
            } else {
                throw new DataNotFoundException("Customer not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
    @Override
    public void checkCreateImage(CustomerRequest request, MultipartFile avatar) {
        if (avatar != null && !avatar.isEmpty()) {
            // Kiểm tra kích thước file và định dạng
            if(avatar.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                throw new RuntimeException("Dung lượng ảnh vượt quá 10MB");
            }
            String contentType = avatar.getContentType();
            if(contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("File không phải là ảnh");
            }

            String fileName = null;
            try {
                fileName = storeFile(avatar);
            } catch (IOException e) {
                throw new RuntimeException("Lỗi khi lưu file ảnh");
            }
            request.setImageURL(fileName);
        }
    }

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
        return customerRepository.searchCustomers(fullName, email, phone, gender, status, pageable)
                .map(customerMapper::toCustomer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
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
        Path uploadDir = Paths.get("uploads/customers");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
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
        Path filePath = Paths.get("uploads ", filename);
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
