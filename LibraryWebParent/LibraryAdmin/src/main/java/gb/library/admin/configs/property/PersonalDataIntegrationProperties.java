package gb.library.admin.configs.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "integrations.personal-data")
public class PersonalDataIntegrationProperties {

    private String scheme = "http";
    private String host = "localhost";
    private int port = -1;
    private long timeout = 10000;
    private String basePath = "";

//  URL:  scheme + "://" + host + (port == -1 ? "" : ":" + port) + basePath
}
