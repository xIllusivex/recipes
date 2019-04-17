package app.recipes.services;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@ComponentScan
@NoArgsConstructor
@Service
public class AWSImageService
{

    private final String BUCKET_NAME = "recipesphotostorage";

    @Value("${aws.config.accesskey}")
    private String accesskey;

    @Value("${aws.config.secretkey}")
    private String secretKey;

    private AWSCredentials credentials;

    private AmazonS3 s3client;

    @PostConstruct
    public void connect() {
        credentials = new BasicAWSCredentials(accesskey, secretKey);

        s3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_WEST_1)
            .build();
    }

    public S3Object getFile(String file)
    {
        if (s3client.doesBucketExistV2(BUCKET_NAME))
        {
            log.debug("Bucket Exists!!");

            return s3client.getObject(BUCKET_NAME, file);
        }

        return null;
    }
}
