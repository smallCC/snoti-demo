package com.gizwits.snotidemo.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jcxcc
 * @since 1.0
 */
@Data
@Component
@NoArgsConstructor
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String basePackage = "com.gizwits";

    private Key apiKey = new Key();

    private Info apiInfo = new Info();

    /**
     * 这里假定是头部参数
     */
    @Data
    @NoArgsConstructor
    public static class Key {

        private String name = "Authorization";

        private String keyName = "Authorization";
    }

    @Data
    @NoArgsConstructor
    public static class Info {
        private String title = "Snoti demo REST API";

        private String description = "Snoti demo REST API";

        private String version = "v1";

        private String termsOfServiceUrl = "";

        private String developer = "Jcxcc";

        private String developerUrl = "https://gitlab.gizwits.com";

        private String developerEmail = "jcliu@gizwits.com";

        private String license = "";

        private String licenseUrl = "";
    }
}
