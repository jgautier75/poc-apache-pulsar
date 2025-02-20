package com.acme.jga.pulsar.startup;

import com.acme.jga.pulsar.config.AppPulsarValues;
import com.acme.jga.pulsar.config.AppTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.admin.Topics;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.policies.data.TenantInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.pulsar.core.PulsarAdministration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Slf4j
public class AppStartedListener {

    @Autowired
    private AppPulsarValues appPulsarValues;

    @Autowired
    private ApplicationContext webAppContext;

    @Autowired
    private PulsarAdministration pulsarAdministration;

    @EventListener(ApplicationReadyEvent.class)
    public void startApp() {
        log.info("Application started, check create topics");
        try (PulsarAdmin pulsarAdmin = pulsarAdministration.createAdminClient()) {
            Topics topics = pulsarAdmin.topics();

            // Create tenants
            createTenants(pulsarAdmin);

            // Create namespace
            createNamespaces(pulsarAdmin);

            // Log existing topics
            listTopics(topics);

            // Filter topics to be created
            List<AppTopic> topicsToCreate = appPulsarValues.getTopics().stream()
                    .filter(appTopic -> !isTopicOnServer(appTopic.getName(), topics))
                    .toList();

            // Create topics if not exist
            createTopics(pulsarAdmin, topicsToCreate);
        } catch (PulsarClientException e) {
            log.error("Pulsar admin error", e);
        }
    }

    private void listTopics(Topics topics) {
        appPulsarValues.getNamespaces().forEach(appNamespace -> {
            try {
                topics.getList(appNamespace.getName()).forEach(t -> log.info("Topic on server: [{}]", t));
            } catch (PulsarAdminException e) {
                log.error("Topics listing", e);
            }
        });
    }


    private void createNamespaces(PulsarAdmin pulsarAdmin) {
        appPulsarValues.getNamespaces().forEach(appns -> {
            try {
                pulsarAdmin.namespaces().createNamespace(appns.getName(), appns.getNbOfBundles());
            } catch (PulsarAdminException e) {
                if (e.getStatusCode() != HttpStatus.CONFLICT.value()) {
                    log.error("Namespace creation ", e);
                }
            }
        });
    }

    private void createTenants(PulsarAdmin pulsarAdmin) {
        appPulsarValues.getTenants().forEach(appTenant -> {
            try {
                pulsarAdmin.tenants().createTenant(appTenant.getName(), TenantInfo.builder().allowedClusters(Set.of(appTenant.getCluster())).build());
            } catch (PulsarAdminException e) {
                if (e.getStatusCode() != HttpStatus.CONFLICT.value()) {
                    log.error("Tenants creation ", e);
                }
            }
        });
    }

    private void createTopics(PulsarAdmin pulsarAdmin, List<AppTopic> topicsToCreate) {
        topicsToCreate.forEach(appTopic -> {
            try {
                pulsarAdmin.topics().createPartitionedTopic(appTopic.getName(), appTopic.getNbOfPartitions());
            } catch (PulsarAdminException e) {
                if (e.getStatusCode() != HttpStatus.CONFLICT.value()) {
                    log.error("Topics creation ", e);
                }
            }
        });
    }

    private boolean isTopicOnServer(String topicName, Topics topics) {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        appPulsarValues.getNamespaces().forEach(appNamespace -> {
            try {
                isPresent.set(topics.getList(appNamespace.getName()).contains(topicName));
            } catch (PulsarAdminException e) {
                if (e.getStatusCode() != HttpStatus.CONFLICT.value()) {
                    log.error("Does topic exists on server ", e);
                }
            }
        });
        return isPresent.get();
    }
}
