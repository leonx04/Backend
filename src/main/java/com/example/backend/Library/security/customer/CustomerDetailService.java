//package com.example.backend.Library.security.customer;
//
//import com.example.backend.Library.model.entity.customer.Customer;
//import com.example.backend.Library.repository.customer.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class CustomerDetailService implements UserDetailsService {
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Customer> customer = customerRepository.findByEmail(username);
//        if (customer.isEmpty()) {
//           throw new UsernameNotFoundException("User not found with Email: " + username);
//        }
//
//        // Trả về một đối tượng User
//        return User.builder()
//                .username(customer.get().getEmail())
//                .password(customer.get().getPassword())
//                .roles("USER")
//                .build();
//    }
//}


package com.example.backend.Library.security.customer;

import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.repository.customer.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailService implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public CustomerDetailService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(username);
        if (customer.isEmpty()) {
            throw new UsernameNotFoundException("User not found with Email: " + username);
        }

        // Chỉ trả về thông tin mà không thay đổi mật khẩu
        return User.builder()
                .username(customer.get().getEmail())
                .password(customer.get().getPassword()) // Mật khẩu đã được mã hóa trước khi lưu
                .roles("USER")
                .build();
    }
}
