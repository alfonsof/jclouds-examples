/**
 * BlobStoreUpload is an example that handles a BlobStore container.
 * Upload a local file to a BlobStore container in several cloud providers:
 *  - S3 bucket on AWS (Amazon Web Services)
 *  - Blob Storage container on Microsoft Azure
 *  - Cloud Storage bucket on Google Cloud Platform (GCP)
 * You must provide 3 parameters:
 * CONTAINER_NAME  = Container name
 * BLOB_NAME       = Blob name in the container
 * LOCAL_FILE_NAME = Local file name
 */

package example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;

import com.google.common.io.Files;
import com.google.common.io.ByteSource;


public class BlobStoreUpload {

    private static String awsAccessKeyId;           // AWS Access Key ID
    private static String awsSecretKey;             // AWS Secret Key
    private static String azureAccountName;         // Azure Storage Account Name
    private static String azureAccountKey;          // Azure Storage Account Key
    private static String gcloudClientEmail;        // Google Cloud Client email
    private static String gcloudPrivateKey;         // Google Cloud Private Key

    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar blobstoreupload.jar <CONTAINER_NAME> <BLOB_NAME> <LOCAL_FILE_NAME>");
            System.exit(1);
        }

        // The name for the new container
        String containerName = args[0];
        // The name for the blob
        String blobName = args[1];
        // The name for the local file
        String localFileName = args[2];

        System.out.println("Container name:  " + containerName);
        System.out.println("Blob name:       " + blobName);
        System.out.println("Local file name: " + localFileName);

        // Load Configuration from a file and get the authentication data for the cloud providers
        loadConfiguration();

        // ******************** AWS S3 provider ********************

        System.out.println("AWS S3 bucket:");

        uploadBlobStore("aws-s3", awsAccessKeyId, awsSecretKey,
                containerName, blobName, localFileName);

        // ******************** Azure Blob Storage provider ********************

        System.out.println("Azure Blob Storage container:");

        uploadBlobStore("azureblob", azureAccountName, azureAccountKey,
                containerName, blobName, localFileName);

        // ******************** Google Cloud Storage provider ********************

        System.out.println("Google Cloud Storage bucket:");

        uploadBlobStore("google-cloud-storage", gcloudClientEmail, gcloudPrivateKey,
                containerName, blobName, localFileName);
    }


    /**
     * Load Configuration from a file and get the authentication credentials for every provider
     */
    private static void loadConfiguration() {

        // The connection string is taken from app.properties file
        Properties prop = new Properties();

        try {
            InputStream is = ClassLoader.getSystemResourceAsStream("app.properties");
            prop.load(is);
        } catch(IOException e) {
            System.out.println(e.toString());
        }
        // AWS
        awsAccessKeyId = prop.getProperty("aws_access_key_id");
        awsSecretKey = prop.getProperty("aws_secret_access_key");
        // Azure
        azureAccountName = prop.getProperty("azure_account_name");
        azureAccountKey = prop.getProperty("azure_account_key");
        // Google Cloud
        gcloudClientEmail = prop.getProperty("gcloud_client_email");
        gcloudPrivateKey = prop.getProperty("gcloud_private_key");
    }


    /**
     * Upload a local file to a BlobStore container
     */
    private static void uploadBlobStore(String provider, String identity, String credential,
                                        String containerName, String blobName, String localFileName) {
        // Init
        BlobStoreContext context = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        System.out.printf("Uploading local file to BlobStore container on \"%s\" ...\n", provider);

        // Instantiate a BlobStore
        BlobStore blobStore = context.getBlobStore();

        try {
            // Create a Blob
            ByteSource payload = Files.asByteSource(new File(localFileName));
            Blob blob = blobStore.blobBuilder(blobName)
                    .payload(payload)
                    .contentLength(payload.size())
                    .build();

            // Upload the Blob
            blobStore.putBlob(containerName, blob);
        } catch (IOException e) {
            System.out.println("Error: File does not exist!!");
        } finally {
            System.out.println("Uploaded.");

            // Disconnect
            context.close();
        }
    }
}
