/**
 * BlobStoreDownload is an example that handles a BlobStore container.
 * Download a blob from a BlobStore container to a local file in several cloud providers:
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;


public class BlobStoreDownload {

    private static final int MAX_BUFFER_SIZE = 1024*1000;  // Maximum buffer size for the file

    private static String awsAccessKeyId;           // AWS Access Key ID
    private static String awsSecretKey;             // AWS Secret Key
    private static String azureAccountName;         // Azure Storage Account Name
    private static String azureAccountKey;          // Azure Storage Account Key
    private static String gcloudClientEmail;        // Google Cloud Client email
    private static String gcloudPrivateKey;         // Google Cloud Private Key

    public static void main(String[] args) throws IOException {

        if (args.length < 3) {
            System.out.println("Not enough parameters.\nProper Usage is: java -jar blobstoredownload.jar <CONTAINER_NAME> <BLOB_NAME> <LOCAL_FILE_NAME>");
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

        downloadBlobStore("aws-s3", awsAccessKeyId, awsSecretKey,
                containerName, blobName, localFileName + ".aws");

        // ******************** Azure Blob Storage provider ********************

        System.out.println("Azure Blob Storage container:");

        downloadBlobStore("azureblob", azureAccountName, azureAccountKey,
                containerName, blobName, localFileName + ".azure");

        // ******************** Google Cloud Storage provider ********************

        System.out.println("Google Cloud Storage bucket:");

        downloadBlobStore("google-cloud-storage", gcloudClientEmail, gcloudPrivateKey,
                containerName, blobName, localFileName + ".gcloud");
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
     * Download a blob from a BlobStore container to a local file
     */
    private static void downloadBlobStore(String provider, String identity, String credential,
                                        String containerName, String blobName, String localFileName) {
        // Init
        BlobStoreContext context = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(BlobStoreContext.class);

        System.out.printf("Downloading a blob from a BlobStore container to a local file from \"%s\" ...\n", provider);

        // Instantiate a BlobStore
        BlobStore blobStore = context.getBlobStore();

        try {
            // Get a Blob
            Blob blob = blobStore.getBlob(containerName, blobName);
            if( blob == null ) {
                System.out.println("Error: Container/Blob does not exist!!");
            } else {
                // Download the Blob
                InputStream inputStream = blob.getPayload().openStream();
                FileOutputStream fileOutputStream = new FileOutputStream(new File(localFileName));
                byte[] readBuffer = new byte[MAX_BUFFER_SIZE];
                int readLen = 0;
                while ((readLen = inputStream.read(readBuffer)) > 0) {
                    fileOutputStream.write(readBuffer, 0, readLen);
                }
                fileOutputStream.close();
                inputStream.close();
            }
        } catch (IOException e) {
            System.out.println("Error: IO Exception!!");
        } finally {
            System.out.println("Downloaded.");
            System.out.println("Local File: " + localFileName);

            // Disconnect
            context.close();
        }
    }
}
