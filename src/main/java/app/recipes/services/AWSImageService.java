package app.recipes.services;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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

    @Value("${app.basedir}")
    private String BASE_DIR;

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
        if (s3client.doesBucketExistV2(BUCKET_NAME) && file != null)
        {
            log.debug("retrieving file: " + file);

            return s3client.getObject(BUCKET_NAME, file);
        }

        return null;
    }

    public void saveFile(MultipartFile multipart)
    {
        File file = new File(BASE_DIR + "/" + multipart.getOriginalFilename());

        file.deleteOnExit();

        try {
            file.createNewFile();

            multipart.transferTo(file);
        } catch(IOException e)
        {
            log.error(e.getMessage());
        }

        s3client.putObject(BUCKET_NAME, file.getName(), file);

        file.delete();
    }
}
