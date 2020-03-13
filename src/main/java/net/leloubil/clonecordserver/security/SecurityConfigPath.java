package net.leloubil.clonecordserver.security;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Scope("singleton")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@ConfigurationProperties("app.security")
public class SecurityConfigPath {

    @NotBlank
    String privateKeyFile;

    @NotBlank
    String publicKeyFile;

}
