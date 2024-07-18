package com.springboot.carsharing.service;

import com.springboot.carsharing.configs.MyUserDetails;
import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.exception_handling.NoSuchCarException;
import com.springboot.carsharing.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private DriverRepository driverRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Driver> driver = driverRepository.findDriverByEmail(email);
        return driver.map(MyUserDetails::new).orElseThrow(()->new NoSuchCarException("Driver with email "+ email +" not found"));
    }
}
