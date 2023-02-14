package de.app.fivegla.persistence;

import de.app.fivegla.persistence.root.SensorMasterDataRoot;
import lombok.extern.slf4j.Slf4j;
import one.microstream.reflect.ClassLoaderProvider;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

/**
 * Provides the storage manager.
 */
@Slf4j
@Component
public class StorageManagerProvider {

    @Value("${app.storage.sensor-master-data}")
    private String dataDirectory;

    @Bean
    @Scope(value = "singleton")
    protected EmbeddedStorageManager embeddedStorageManagerForSensorMasterData() {
        SensorMasterDataRoot sensorMasterDataRoot = new SensorMasterDataRoot();
        log.info("Using data directory: {}", dataDirectory);
        return EmbeddedStorage.Foundation(Paths.get(dataDirectory))
                .onConnectionFoundation(cf -> cf.setClassLoaderProvider(ClassLoaderProvider.New(
                        Thread.currentThread().getContextClassLoader()))).start(sensorMasterDataRoot);
    }

}
