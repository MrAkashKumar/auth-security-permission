package com.akash.authSecurityPermission.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * @author Akash Kumar
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    private Jwt jwt = new Jwt();
    private Cors cors = new Cors();
    private Security security = new Security();
    private FileStorage file = new FileStorage();

    @Data
    public static class Jwt {
        @NotBlank
        private String secret;
        @Positive
        private long expiration = 86400000L;
        @Positive
        private long refreshExpiration = 604800000L;
    }

    @Data
    public static class Cors {
        private List<String> allowedOrigins = List.of("http://localhost:3000");
    }

    @Data
    public static class Security {
        private int passwordMinLength = 8;
        private int maxLoginAttempts = 5;
        private int lockoutDurationMinutes = 30;
    }

    @Data
    public static class FileStorage {
        private String uploadDir = "./uploads";
        private String maxSize = "10MB";
    }
}
