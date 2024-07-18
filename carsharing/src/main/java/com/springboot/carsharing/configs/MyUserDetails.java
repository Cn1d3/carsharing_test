package com.springboot.carsharing.configs;

import com.springboot.carsharing.entity.Driver;
import com.springboot.carsharing.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private Driver driver;


    public MyUserDetails(Driver driver){
        this.driver=driver;
    }

    public Driver getDriver(){
        return driver;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays .stream(driver.getRole().split(", ")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return driver.getPassword();
    }

    @Override
    public String getUsername() {
        return driver.getEmail();
    }

    @Override//Истечение срока действия аккаунта, можно проверять на ласт активити и после года простоя блокать. Тру - если все ок, фолс если истек
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override//Проверка на блокировку аккаунта, проще говоря бан, можно раздавать баны с админ панели
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override//Срок действия пароля, лучше не трогать, шляпа полная, либо удалять после просто аккаунта так-же
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override//Включен либо выключен пользователь, то бишь онлайн или нет, после успешной реги можно делать тру, пока по-дефолту тру
    public boolean isEnabled() {
        return true;
    }
}
