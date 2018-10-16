/**
 * BlobStoreCreate is an example that handles a BlobStore container.
 * Create a new BlobStore container in several cloud providers:
 *  - S3 bucket on AWS (Amazon Web Services)
 *  - Blob Storage container on Microsoft Azure
 *  - Cloud Storage bucket on Google Cloud Platform (GCP)
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
import org.jclouds.domain.Location;


public class BlobStoreCreate {

    private static final String LOCATION_AWS = "eu-west-1";      // AWS region name us-east-1 = US East(N. Virginia)

    private static String awsAccessKeyId;           // AWS Access Key ID
    private static String awsSecretKey;             // AWS Secret Key
    private static String azureAccountName;         // Azure Storage Account Name
    private static String azureAccountKey;          // Azure Storage Account Key
    private static String gcloudClientEmail;        // Google Cloud Client email
    private static String gcloudPrivateKey;         // Google Cloud Private Key

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar blobstorecreate.jar <BUCKET_NAME>");
            System.exit(1);
        }

        // The name for the new container
        String containerName = args[0];

        System.out.println("Container name: " + containerName);

        // Load Configuration from a file and get the authentication data for the cloud providers
        loadConfiguration();

        // ******************** AWS S3 provider ********************

        System.out.println("AWS S3 bucket:");

        // With AWS it is possible to select a location
        createBlobStore("aws-s3", awsAccessKeyId, awsSecretKey,
                            containerName, LOCATION_AWS);

        // ******************** Azure Blob Storage provider ********************

        System.out.println("Azure Blob Storage container:");

        createBlobStore("azureblob", azureAccountName, azureAccountKey,
                            containerName, null);

        // ******************** Google Cloud Storage provider ********************

        System.out.println("Google Cloud Storage bucket:");

        createBlobStore("google-cloud-storage", gcloudClientEmail, gcloudPrivateKey,
                            containerName, null);
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
     * Create a BlobStore container
     */
    private static void createBlobStore(String provider, String identity, String credential,
                                            String containerName, String containerLocationString) {
        // Init
        BlobStoreContext context = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        System.out.printf("Creating BlobStore container on \"%s\" ...\n", provider);

        // Instantiate a BlobStore
        BlobStore blobStore = context.getBlobStore();

        Location location = null;
        if (containerLocationString != null) {
            // Look for the location
            for (Location pLocation : blobStore.listAssignableLocations()) {
                if (containerLocationString.contains(pLocation.getId())) {
                    location = pLocation;
                    break;
                }
            }
        }

        // Create a BlobStore container
        boolean created = blobStore.createContainerInLocation(location, containerName);
        if (created) {
            System.out.println("Created.");
        } else {
            System.out.println("Error: BlobStore container already exists!!");
        }

        // Disconnect
        context.close();
    }
}
