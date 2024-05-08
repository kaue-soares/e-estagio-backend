package kauesoares.eestagio.authservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "security.auth.seed")
@Data
public class AuthProperties {

    private String adminEmail;
    private String adminPassword;

    private String userEmail;
    private String userPassword;

}
