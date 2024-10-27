//package com.example.backend.Library.security.employee;
//
//import com.example.backend.Library.model.entity.Employee;
//import com.example.backend.Library.repository.EmployeeRepo;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class EmployeeDetailService implements UserDetailsService {
//
//    private final EmployeeRepo employeeRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public EmployeeDetailService(EmployeeRepo employeeRepository, PasswordEncoder passwordEncoder) {
//        this.employeeRepository = employeeRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Tìm nhân viên theo Email
//        Optional<Employee> existsEmployee = employeeRepository.findByEmail(username);
//        // Kiểm tra xem nhân viên có tồn tại không
//        if (existsEmployee.isEmpty()) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        // Lấy thông tin nhân viên
//        Employee employee = existsEmployee.get();
//
//        // Kiểm tra mật khẩu đã mã hóa chưa
//        if (!employee.getPassword().startsWith("$2a$")) {  // Giả sử sử dụng BCrypt
//            // Mã hóa mật khẩu và cập nhật vào cơ sở dữ liệu
//            String encodedPassword = passwordEncoder.encode(employee.getPassword());
//            // Cập nhật mật khẩu mới
//            employee.setPassword(encodedPassword);
//            // Lưu thông tin nhân viên
//            employeeRepository.save(employee);
//        }
//
//        // Tạo danh sách quyền
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        if (employee.getRoleId() == 0) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        } else if (employee.getRoleId() == 1) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_STAFF"));
//        }
//
//        // Trả về thông tin nhân viên
//        return new User(employee.getEmail(), employee.getPassword(), authorities);
//    }
//}
