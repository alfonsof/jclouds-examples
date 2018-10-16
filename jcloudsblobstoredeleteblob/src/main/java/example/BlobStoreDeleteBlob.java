/**
 * BlobStoreDeleteBlob is an example that handles a BlobStore container.
 * Delete a Blob in a BlobStore container in several cloud providers:
 *  - S3 bucket on AWS (Amazon Web Services)
 *  - Blob Storage container on Microsoft Azure
 *  - Cloud Storage bucket on Google Cloud Platform (GCP)
 * You must provide 1 parameter:
 * CONTAINER_NAME = Name of the container
 * BLOB_NAME = Name of blob in the container
 */

package example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;


public class BlobStoreDeleteBlob {
    private static String awsAccessKeyId;           // AWS Access Key ID
    private static String awsSecretKey;             // AWS Secret Key
    private static String azureAccountName;         // Azure Storage Account Name
    private static String azureAccountKey;          // Azure Storage Account Key
    private static String gcloudClientEmail;        // Google Cloud Client email
    private static String gcloudPrivateKey;         // Google Cloud Private Key

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar blobstoredeleteblob.jar <CONTAINER_NAME> <BLOB_NAME>");
            System.exit(1);
        }

        // The name for the container
        String containerName = args[0];

        // The name for the blob
        String blobName = args[1];

        System.out.println("Container name: " + containerName);
        System.out.println("Blob name:      " + blobName);

        // Load Configuration from a file and get the authentication data for the cloud providers
        loadConfiguration();

        // ******************** AWS S3 provider ********************

        System.out.println("AWS S3 bucket:");

        deleteBlobBlobStore("aws-s3", awsAccessKeyId, awsSecretKey,
                containerName, blobName);

        // ******************** Azure Blob Storage provider ********************

        System.out.println("Azure Blob Storage container:");

        deleteBlobBlobStore("azureblob", azureAccountName, azureAccountKey,
                containerName, blobName);

        // ******************** Google Cloud Storage provider ********************

        System.out.println("Google Cloud Storage bucket:");

        deleteBlobBlobStore("google-cloud-storage", gcloudClientEmail, gcloudPrivateKey,
                containerName, blobName);
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
     * Delete a Blob in a BlobStore container
     */
    private static void deleteBlobBlobStore(String provider, String identity, String credential,
                                        String containerName, String blobName) {
        // Init
        BlobStoreContext context = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        System.out.printf("Deleting the Blob in the BlobStore container on \"%s\" ...\n", provider);

        // Instantiate a BlobStore
        BlobStore blobStore = context.getBlobStore();

        if (blobStore.blobExists(containerName, blobName)) {
            // Delete a Blob in a BlobStore container
            blobStore.removeBlob(containerName, blobName);
            System.out.println("Deleted.");
        } else {
            System.out.println("Error: Container/Blob does not exists!!");
        }

        // Disconnect
        context.close();
    }
}

