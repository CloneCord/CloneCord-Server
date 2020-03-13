package net.leloubil.clonecordserver.security;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@NoArgsConstructor
@Scope("singleton")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class KeysData {

    PrivateKey privateKey;

    PublicKey publicKey;
}
