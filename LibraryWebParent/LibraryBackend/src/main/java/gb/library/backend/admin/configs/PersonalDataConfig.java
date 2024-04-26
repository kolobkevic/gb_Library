package gb.library.backend.admin.configs;

import gb.library.backend.admin.configs.property.PersonalDataIntegrationProperties;
import gb.library.pd.openapi.client.pd.ApiClient;
import gb.library.pd.openapi.client.pd.api.ReaderApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Configuration
@RequiredArgsConstructor
public class PersonalDataConfig {

    private final PersonalDataIntegrationProperties properties;

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();

        apiClient.setScheme(properties.getScheme());
        apiClient.setHost(properties.getHost());
        apiClient.setPort(properties.getPort());
        apiClient.setBasePath(properties.getBasePath());
        apiClient.setConnectTimeout(Duration.of(properties.getTimeout(), ChronoUnit.MILLIS));

        return apiClient;
    }

    @Bean
    public ReaderApi readersApi() {
        return new ReaderApi(apiClient());
    }
}
