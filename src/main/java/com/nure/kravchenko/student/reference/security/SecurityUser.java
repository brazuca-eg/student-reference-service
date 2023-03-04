package com.nure.kravchenko.student.reference.security;

import com.nure.kravchenko.student.reference.entity.Student;
import com.nure.kravchenko.student.reference.entity.Worker;
import com.nure.kravchenko.student.reference.entity.app.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromStudent(Student student) {
        return new org.springframework.security.core.userdetails.User(
                student.getEmail(), student.getPassword(),
                Role.STUDENT.getAuthorities()
        );
    }

    public static UserDetails fromWorker(Worker worker) {
        if (worker.isAdmin()) {
            return new org.springframework.security.core.userdetails.User(worker.getEmail(), worker.getPassword(),
                    Role.ADMIN.getAuthorities()
            );
        } else {
            return new org.springframework.security.core.userdetails.User(worker.getEmail(), worker.getPassword(),
                    Role.WORKER.getAuthorities()
            );
        }
    }

}