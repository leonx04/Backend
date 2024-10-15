package com.example.backend.Library.security.customer;

import com.example.backend.Library.model.entity.customer.Customer;
import com.example.backend.Library.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Timf kiếm khách hàng theo email
        Optional<Customer> customer = customerRepository.findByEmail(username);
        // Nếu không tìm thấy khách hàng
        if (customer.isEmpty()) {
            // Ném ra ngoại lệ
           throw new UsernameNotFoundException("User not found with Email: " + username);
        }

        // Trả về một đối tượng User
        return User.builder()
                .username(customer.get().getEmail())
                .password(customer.get().getPassword())
                .roles("USER")
                .build();
    }
}
