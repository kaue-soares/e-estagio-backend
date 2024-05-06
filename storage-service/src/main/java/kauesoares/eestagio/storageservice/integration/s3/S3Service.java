package kauesoares.eestagio.storageservice.integration.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import kauesoares.eestagio.storageservice.config.S3Properties;
import kauesoares.eestagio.storageservice.messages.Messages;
import kauesoares.eestagio.storageservice.messages.exception.S3Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Scope("singleton")
@Log4j2
public class S3Service {

    private final S3Properties s3Properties;

    private static AmazonS3 s3Client;

    private AmazonS3 getS3Client() {
        if (s3Client == null)
            s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(this.getCredentials())
                    .withRegion(Regions.fromName(s3Properties.getRegion()))
                    .build();

        return s3Client;
    }

    private AWSStaticCredentialsProvider getCredentials() {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(
                        s3Properties.getAccessKey(),
                        s3Properties.getSecretKey()
                )
        );
    }

    private S3Object getFile(String bucketName, String fileKey) {
        return this.getS3Client().getObject(bucketName, fileKey);
    }

    public S3Object getKey(String key) {
        try {
            return this.getFile(s3Properties.getKeysBucketName(), key);
        } catch (AmazonServiceException e) {
            log.error("Error getting key from S3", e);
            throw new S3Exception(Messages.GET_KEY_ERROR);
        }
    }

}
