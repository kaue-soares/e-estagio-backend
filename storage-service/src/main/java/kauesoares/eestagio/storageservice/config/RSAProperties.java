package kauesoares.eestagio.storageservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "security.rsa")
@Data
public class RSAProperties {

    private String publicKeyPath;
    private String privateKeyPath;

}
