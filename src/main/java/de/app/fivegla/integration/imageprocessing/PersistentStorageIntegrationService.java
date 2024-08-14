package de.app.fivegla.integration.imageprocessing;

import de.app.fivegla.api.Error;
import de.app.fivegla.api.ErrorMessage;
import de.app.fivegla.api.exceptions.BusinessException;
import de.app.fivegla.persistence.entity.Image;
import de.app.fivegla.persistence.entity.Tenant;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Slf4j
@Service
public class PersistentStorageIntegrationService {

    @Value("${app.s3.endpoint}")
    private String endpoint;

    @Value("${app.s3.accessKey}")
    private String accessKey;

    @Value("${app.s3.secretKey}")
    private String secretKey;

    @Value("${app.s3.preConfiguredBucketName}")
    private String preConfiguredBucketName;

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
                    .bucket(preConfiguredBucketName)
                    .build())) {
                client.makeBucket(MakeBucketArgs.builder()
                        .bucket(preConfiguredBucketName)
                        .build());
                log.debug("Bucket {} created.", transactionId);
            }
            var imageAsRawData = image.getImageAsRawData();
            client.putObject(PutObjectArgs.builder()
                    .bucket(preConfiguredBucketName)
                    .object(image.getFullFilename(image.getTenant(), transactionId))
                    .stream(new ByteArrayInputStream(imageAsRawData), imageAsRawData.length, -1)
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

    /**
     * Retrieves the result file from the S3 storage for the specified tenant and transaction ID.
     *
     * @param tenant        The tenant associated with the result file.
     * @param transactionId The transaction ID of the result file.
     * @return An Optional containing the byte array of the result file if it exists, or an empty Optional if the result file does not exist or an error occurs.
     */
    public Optional<byte[]> getResultFile(Tenant tenant, String transactionId) {
        try (var client = minioClientBuilder()
                .build()) {
            var getObjectArgs = GetObjectArgs.builder()
                    .bucket(preConfiguredBucketName)
                    .object(getFileNameForResultFile(tenant, transactionId))
                    .build();
            var getObjectResponse = client.getObject(getObjectArgs);
            if (getObjectResponse != null) {
                return Optional.of(getObjectResponse.readAllBytes());
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Could not get result file from S3.", e);
            return Optional.empty();
        }
    }

    private String getFileNameForResultFile(Tenant tenant, String transactionId) {
        return tenant.getTenantId() + "/" + transactionId + "/result.zip";
    }
}

