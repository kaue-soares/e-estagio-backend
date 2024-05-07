package kauesoares.eestagio.storageservice.service;

import com.amazonaws.services.s3.model.S3Object;
import kauesoares.eestagio.storageservice.config.RSAProperties;
import kauesoares.eestagio.storageservice.dto.res.PrivateKeyResDTO;
import kauesoares.eestagio.storageservice.dto.res.KeyResDTO;
import kauesoares.eestagio.storageservice.integration.s3.S3Service;
import kauesoares.eestagio.storageservice.messages.Messages;
import kauesoares.eestagio.storageservice.messages.exception.KeyGenerationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class KeysService {

    private final S3Service s3Service;
    private final RSAProperties rsaProperties;

    public KeyResDTO getPublicKey() {
        try {
            S3Object s3Object = s3Service.getKey(rsaProperties.getPublicKeyPath());

            BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
            String stringContent = reader.lines().collect(Collectors.joining());

            byte[] keyBytes = Base64.getDecoder().decode(stringContent);

            return new KeyResDTO(keyBytes);

        } catch (Exception e) {
            log.error("Error while generating public key: {}", e.getMessage());
            throw new KeyGenerationException(Messages.GENERATE_KEY_ERROR);
        }
    }

    public KeyResDTO getPrivateKey() {
        try {
            S3Object s3Object = this.s3Service.getKey(rsaProperties.getPrivateKeyPath());

            BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
            String stringContent = reader.lines().collect(Collectors.joining());

            byte[] keyBytes = Base64.getDecoder().decode(stringContent);

            return new KeyResDTO(keyBytes);

        } catch (Exception e) {
            log.error("Error while generating private key: {}", e.getMessage());
            throw new KeyGenerationException(Messages.GENERATE_KEY_ERROR);
        }
    }

}
