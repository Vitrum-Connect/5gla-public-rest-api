package de.app.fivegla.integration.imageprocessing;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.persistence.entity.Image;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Slf4j
@Service
public class PersistentStorageIntegrationService {

    @Value("${app.s3.endpoint}")
    private String endpoint;

    @Value("${app.s3.accessKey}")
    private String accessKey;

    @Value("${app.s3.secretKey}")
    private String secretKey;

    /**
     * Stores an image on S3.
     *
     * @param transactionId The transaction id.
     * @param image         The image to store.
     */
    public void storeImage(String transactionId, Image image) {
        try (var client = minioClientBuilder()
                .build()) {
            if (!client.bucketExists(BucketExistsArgs.builder()
                    .bucket(transactionId)
                    .build())) {
                client.makeBucket(MakeBucketArgs.builder()
                        .bucket(transactionId)
                        .build());
                log.debug("Bucket {} created.", transactionId);
            }
            client.putObject(PutObjectArgs.builder()
                    .bucket(transactionId)
                    .object(image.getName())
                    .stream(new ByteArrayInputStream(image.getBase64encodedImage().getBytes()), image.getBase64encodedImage().length(), -1)
                    .build());
        } catch (Exception e) {
            log.error("Could not store image on S3.", e);
            var errorMessage = ErrorMessage.builder().error(Error.COULD_NOT_STORE_IMAGE_ON_S3).message("Could not store image on S3, please see log for more details.").build();
            throw new BusinessException(errorMessage);
        }
    }

    private MinioClient.Builder minioClientBuilder() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey);
    }

}

