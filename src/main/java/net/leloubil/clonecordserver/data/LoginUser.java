package net.leloubil.clonecordserver.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.leloubil.clonecordserver.exceptions.RessourceNotFoundException;
import net.leloubil.clonecordserver.formdata.FormLogin;
import net.leloubil.clonecordserver.services.UserService;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


/**
 * Class to represent data needed to login or register
 */
@EqualsAndHashCode(callSuper = true)
@Data @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("LoginUsers")
public class LoginUser extends FormLogin implements UserDetails {

    @Id
    UUID uuid = UUID.randomUUID();

    public static LoginUser getCurrent() {
        return (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override @JsonIgnore
    public String getUsername() {
        return getEmail();
    }

    @Override  @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override  @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override  @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override  @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public User getUser(UserService userService) {
        return userService.getUser(this.getUuid()).orElseThrow(() -> new RessourceNotFoundException("userId"));
    }
}
