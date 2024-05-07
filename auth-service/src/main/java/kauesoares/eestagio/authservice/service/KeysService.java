package kauesoares.eestagio.authservice.service;

import kauesoares.eestagio.authservice.http.StorageClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service
@Scope("singleton")
@RequiredArgsConstructor
public class KeysService {
    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    private final StorageClient storageClient;

    @SneakyThrows
    public PrivateKey getPrivateKey() {
        if (privateKey == null) {
            byte[] bytes = this.storageClient.getPrivateKey().key();

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            privateKey = keyFactory.generatePrivate(keySpec);
        }
        return privateKey;
    }

    @SneakyThrows
    public PublicKey getPublicKey() {
        if (publicKey == null) {
            byte[] bytes = this.storageClient.getPublicKey().key();

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKey = keyFactory.generatePublic(keySpec);
        }

        return publicKey;
    }

}
