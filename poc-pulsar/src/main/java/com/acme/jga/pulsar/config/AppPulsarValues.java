package com.acme.jga.pulsar.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.config.pulsar")
@AllArgsConstructor(access = AccessLevel.NONE)
@NoArgsConstructor(access = AccessLevel.NONE)
@Data
public class AppPulsarValues {
    private String url;
    private String adminUrl;
    private String cluster;
    private List<AppTenant> tenants;
    private List<AppNamespace> namespaces;
    private List<AppTopic> topics;
}
