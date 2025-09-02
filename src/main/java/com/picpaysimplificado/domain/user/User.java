package com.picpaysimplificado.domain.user;

import com.picpaysimplificado.DTO.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Table(name = "users")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id" )
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String document;

    @Column(unique = true)
    private String email;

    private String password;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(UserDTO data, String encodedPassword){
        this.firstName = data.firstName();
        this.lastName = data.lastName();
        this.document = data.document();
        this.email = data.email();
        this.password = encodedPassword;
        this.balance = data.balance();
        this.userType = data.userType();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userType == UserType.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else if(this.userType == UserType.MERCHANT){
            return List.of(new SimpleGrantedAuthority("ROLE_MERCHANT"));
        }else{
            return List.of(new SimpleGrantedAuthority("ROLE_COMMON"));
        }
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
