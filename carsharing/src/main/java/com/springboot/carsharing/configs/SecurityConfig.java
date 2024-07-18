package com.springboot.carsharing.configs;


import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.enums.Role;
import com.springboot.carsharing.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, PasswordEncoder encoder, CustomUserDetailService service) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(service).passwordEncoder(encoder);
        var authenticationManager = authenticationManagerBuilder.build();
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/drivers", "/drivers/update", "/drivers/profile/{id}", "/rentHistory", "/drivers/delete/{id}", "/cars/new", "cars/edit/{id}", "/cars/delete/{id}", "/cars/control").hasAuthority(Role.ROLE_ADMIN.getAuthority())
                .requestMatchers("/", "/cars", "/cars/{id}", "/images/**", "/registration", "/login", "/drivers/profile", "/css/styles.css")
                .permitAll().anyRequest().hasAuthority(Role.ROLE_USER.getAuthority())
                )
                .formLogin(it->it.loginPage("/login").failureUrl("/login?error=true").permitAll()
                        .defaultSuccessUrl("/", true))
                .authenticationManager(authenticationManager)
                .sessionManagement(it->it.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .build();
    }
}



