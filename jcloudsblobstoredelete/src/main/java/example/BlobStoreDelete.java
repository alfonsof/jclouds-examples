/**
 * BlobStoreDelete is an example that handles a BlobStore container.
 * Delete a BlobStore container, so a:
 * S3 bucket on AWS (Amazon Web Services)
 * Blob Storage container on Microsoft Azure
 * Cloud Storage bucket on Google Cloud Platform (GCP)
 * You must provide 1 parameter:
 * BUCKET_NAME = Name of the bucket
 */

package example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;

public class BlobStoreDelete {
    private static String awsAccessKeyId;           // AWS Access Key ID
    private static String awsSecretKey;             // AWS Secret Key
    private static String azureAccountName;         // Azure Storage Account Name
    private static String azureAccountKey;          // Azure Storage Account Key
    private static String gcloudClientEmail;        // Google Cloud Client email
    private static String gcloudPrivateKey;         // Google Cloud Private Key

    public static void main(String[] args) throws IOException {
        String provider;
        String identity;
        String credential;

        if (args.length < 1) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar blobstoredelete.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // The name for the new container
        String containerName = args[0];

        System.out.println("Container name: " + containerName);

        // Load Configuration from a file and get the authentication data for the cloud providers
        loadConfiguration();

        // ******************** AWS S3 provider ********************
        provider = "aws-s3";
        identity = awsAccessKeyId;
        credential = awsSecretKey;

        System.out.println("Deleting AWS S3 bucket ...");

        // Init
        BlobStoreContext contextAWS = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        BlobStore blobStoreAWS = contextAWS.getBlobStore();

        // Delete an AWS S3 bucket
        blobStoreAWS.deleteContainer(containerName);
        System.out.println("Deleted AWS S3 bucket");

        contextAWS.close();

        // ******************** Azure Blob Storage provider ********************
        provider = "azureblob";
        identity = azureAccountName;
        credential = azureAccountKey;

        System.out.println("Deleting Azure Blob Storage container ...");

        // Init
        BlobStoreContext contextAzure = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        BlobStore blobStoreAzure = contextAzure.getBlobStore();

        // Delete an Azure Blob Storage container
        blobStoreAzure.deleteContainer(containerName);
        System.out.println("Deleted Azure Blob Storage container");

        contextAzure.close();

        // ******************** Google Cloud Storage provider ********************
        provider = "google-cloud-storage";
        identity = gcloudClientEmail;
        credential = gcloudPrivateKey;

        System.out.println("Deleting Google Cloud Storage bucket ...");

        // Init
        BlobStoreContext contextGoogleCloud = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        BlobStore blobStoreGoogleCloud = contextGoogleCloud.getBlobStore();

        // Delete a Google Cloud Storage
        blobStoreGoogleCloud.deleteContainer(containerName);
        System.out.println("Deleted Google Cloud Storage bucket");

        contextGoogleCloud.close();
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
}