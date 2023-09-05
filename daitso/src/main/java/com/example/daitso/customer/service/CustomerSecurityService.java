package com.example.daitso.customer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.daitso.customer.model.CustomerRole;
import com.example.daitso.customer.model.CustomerSecurity;
import com.example.daitso.customer.repository.ICustomerRepository;

@Service
public class CustomerSecurityService implements UserDetailsService{

	@Autowired
	ICustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String customerEmail) throws UsernameNotFoundException {
		Optional<CustomerSecurity> _customerSecurity = this.customerRepository.findByCustomerEmail(customerEmail);
		if(_customerSecurity.isEmpty()) {
			 throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		CustomerSecurity customerSecurity = _customerSecurity.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		if ("admin".equals(customerEmail)) {
			authorities.add(new SimpleGrantedAuthority(CustomerRole.ADMIN.getValue()));
	    } else {
	    	authorities.add(new SimpleGrantedAuthority(CustomerRole.USER.getValue()));
	    }
		
		User user = new User(String.valueOf(customerSecurity.getCustomerId()), customerSecurity.getCustomerPw(), authorities);
		
	    return user;
	}

}
