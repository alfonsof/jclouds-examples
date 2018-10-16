/**
 * BlobStoreListAll is an example that handles a BlobStore container.
 * List all containers in BlobStore in several cloud providers:
 *  - S3 bucket on AWS (Amazon Web Services)
 *  - Blob Storage container on Microsoft Azure
 *  - Cloud Storage bucket on Google Cloud Platform (GCP)
 */

package example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.StorageMetadata;


public class BlobStoreListAll {
    private static String awsAccessKeyId;           // AWS Access Key ID
    private static String awsSecretKey;             // AWS Secret Key
    private static String azureAccountName;         // Azure Storage Account Name
    private static String azureAccountKey;          // Azure Storage Account Key
    private static String gcloudClientEmail;        // Google Cloud Client email
    private static String gcloudPrivateKey;         // Google Cloud Private Key

    public static void main(String[] args) throws IOException {

        // Load Configuration from a file and get the authentication data for the cloud providers
        loadConfiguration();

        // ******************** AWS S3 provider ********************

        System.out.println("AWS S3 bucket:");

        listAllBlobStore("aws-s3", awsAccessKeyId, awsSecretKey);

        System.out.println();

        // ******************** Azure Blob Storage provider ********************

        System.out.println("Azure Blob Storage container:");

        listAllBlobStore("azureblob", azureAccountName, azureAccountKey);

        System.out.println();

        // ******************** Google Cloud Storage provider ********************

        System.out.println("Google Cloud Storage bucket:");

        listAllBlobStore("google-cloud-storage", gcloudClientEmail, gcloudPrivateKey);

        System.out.println();
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
     * List all containers in BlobStore
     */
    private static void listAllBlobStore(String provider, String identity, String credential) {
        // Init
        BlobStoreContext context = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        System.out.printf("Listing all containers in BlobStore on \"%s\" ...\n", provider);

        // Instantiate a BlobStore
        BlobStore blobStore = context.getBlobStore();

        // List Containers in BlobStore
        for (StorageMetadata resourceMd : blobStore.list()) {
            System.out.println(" * " + resourceMd.getName());
            System.out.println("   " + resourceMd);
        }

        System.out.println("Listed.");

        // Disconnect
        context.close();
    }
}

