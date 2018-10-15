/**
 * BlobStoreGetLocations is an example that handles a BlobStore container.
 * Get the available locations for BlobStore containers in several cloud providers, so:
 * S3 bucket on AWS (Amazon Web Services)
 * Blob Storage container on Microsoft Azure
 * Cloud Storage bucket on Google Cloud Platform (GCP)
 */

package example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.domain.Location;

public class BlobStoreGetLocations {
    private static String awsAccessKeyId;
    private static String awsSecretKey;
    private static String azureAccountName;
    private static String azureAccountKey;
    private static String gcloudClientEmail;
    private static String gcloudPrivateKey;

    public static void main(String[] args) throws IOException {
        String provider;
        String identity;
        String credential;

        // Load Configuration from a file and get the authentication data for the cloud providers
        loadConfiguration();

        // ******************** AWS provider using S3 ********************
        provider = "aws-s3";
        identity = awsAccessKeyId;
        credential = awsSecretKey;

        // Init
        BlobStoreContext contextAWS = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        BlobStore blobStoreAWS = contextAWS.getBlobStore();

        System.out.println("AWS locations:");
        // Get available locations
        for (Location pLocation : blobStoreAWS.listAssignableLocations()) {
            System.out.println("  - " + pLocation.getId());
        }

        System.out.println();

        contextAWS.close();

        // ******************** Azure provider using Blob Storage ********************
        provider = "azureblob";
        identity = azureAccountName;
        credential = azureAccountKey;

        // Init
        BlobStoreContext contextAzure = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        BlobStore blobStoreAzure = contextAzure.getBlobStore();

        System.out.println("Azure locations:");
        // Get available locations
        for (Location pLocation : blobStoreAzure.listAssignableLocations()) {
            System.out.println("  - " + pLocation.getId());
        }

        System.out.println();

        contextAzure.close();

        // ******************** Google Cloud provider using Cloud Storage ********************
        provider = "google-cloud-storage";
        identity = gcloudClientEmail;
        credential = gcloudPrivateKey;

        // Init
        BlobStoreContext contextGoogleCloud = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        BlobStore blobStoreGoogleCloud = contextGoogleCloud.getBlobStore();

        System.out.println("Google Cloud locations:");
        // Get available locations
        for (Location pLocation : blobStoreGoogleCloud.listAssignableLocations()) {
            System.out.println("  - " + pLocation.getId());
        }

        System.out.println();

        contextGoogleCloud.close();
    }


    /**
     * Load Configuration from a file and get the Storage Connection String
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
