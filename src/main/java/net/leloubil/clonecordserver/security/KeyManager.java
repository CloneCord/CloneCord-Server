package net.leloubil.clonecordserver.security;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
@CommonsLog
public class KeyManager implements ApplicationListener<ContextRefreshedEvent> {

    private final ShutdownEndpoint shutdownEndpoint;

    private final SecurityConfigPath securityConfig;

    private final KeysData keysData;

    public KeyManager(ShutdownEndpoint shutdownEndpoint, SecurityConfigPath securityConfig, KeysData keysData) {
        this.shutdownEndpoint = shutdownEndpoint;
        this.securityConfig = securityConfig;
        this.keysData = keysData;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            File f = ResourceUtils.getFile("./privkey.pem");
            if (!f.exists()) {
                KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                kpg.initialize(2048);
                KeyPair kp = kpg.generateKeyPair();
                Key pub = kp.getPublic();
                Key pvt = kp.getPrivate();

                FileOutputStream out = new FileOutputStream(securityConfig.getPrivateKeyFile());
                out.write(pvt.getEncoded());
                out.close();

                out = new FileOutputStream(securityConfig.getPublicKeyFile());
                out.write(pvt.getEncoded());
                out.close();
            }

            Path priv = Paths.get(securityConfig.getPrivateKeyFile());
            byte[] privbytes = Files.readAllBytes(priv);

            PKCS8EncodedKeySpec privspec = new PKCS8EncodedKeySpec(privbytes);
            KeyFactory privfactory = KeyFactory.getInstance("RSA");
            PrivateKey privkey = privfactory.generatePrivate(privspec);
            keysData.setPrivateKey(privkey);

            Path pub = Paths.get(securityConfig.getPublicKeyFile());
            byte[] pubbytes = Files.readAllBytes(pub);

            /* Generate public key. */
            X509EncodedKeySpec pubspec = new X509EncodedKeySpec(pubbytes);
            KeyFactory pubfactory = KeyFactory.getInstance("RSA");
            PublicKey pubkey = pubfactory.generatePublic(pubspec);
            keysData.setPublicKey(pubkey);

        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            e.printStackTrace();
            shutdownEndpoint.shutdown();
        }
    }
}

