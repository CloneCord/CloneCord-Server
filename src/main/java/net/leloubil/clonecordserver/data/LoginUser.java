package net.leloubil.clonecordserver.data;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.services.UserService;
import net.leloubil.clonecordserver.validation.ValidPassword;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


/**
 * Class to represent data needed to login or register
 */
@Data @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("LoginUsers")
public class LoginUser implements UserDetails {

    @Id
    UUID uuid;

    @NotNull
    String email;

    @NotNull
    @ValidPassword
    String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
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
