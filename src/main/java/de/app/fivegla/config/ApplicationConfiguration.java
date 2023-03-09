package de.app.fivegla.config;

import one.microstream.integrations.spring.boot.types.config.StorageManagerProvider;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

@Configuration
public class ApplicationConfiguration {

    public static final String EMBEDDED_STORAGE_MANAGER_QUALIFIER = "Primary";
    private final StorageManagerProvider storageManagerProvider;

    public ApplicationConfiguration(StorageManagerProvider storageManagerProvider) {
        this.storageManagerProvider = storageManagerProvider;
    }

    @Bean
    @Lazy
    @Primary
    public EmbeddedStorageFoundation<?> embeddedStorageFoundation() {
        return storageManagerProvider.embeddedStorageFoundation();
    }

    @Bean(destroyMethod = "shutdown")
    @Lazy
    @Primary
    public EmbeddedStorageManager embeddedStorageManager() {
        return storageManagerProvider.get(EMBEDDED_STORAGE_MANAGER_QUALIFIER);
    }
}
