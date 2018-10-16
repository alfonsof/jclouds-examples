/**
 * ComputeServiceGetLocations is an example that handles a Compute Service.
 * Get the available locations for ComputeService in several cloud providers:
 *  - EC2 on AWS (Amazon Web Services)
 *  - Compute VM on Microsoft Azure
 *  - Compute Engine on Google Cloud Platform (GCP)
 */

package example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.domain.Location;


public class ComputeServiceGetLocations {
    private static String awsAccessKeyId;
    private static String awsSecretKey;
    private static String azureAccountName;
    private static String azureAccountKey;
    private static String gcloudClientEmail;
    private static String gcloudPrivateKey;

    public static void main(String[] args) throws IOException {

        // Load Configuration from a file and get the authentication data for the cloud providers
        loadConfiguration();

        // ******************** AWS EC2 provider ********************

        System.out.println("AWS locations:");
        getLocationsComputeService("aws-ec2", awsAccessKeyId, awsSecretKey);
        System.out.println();

        // ******************** Azure Compute provider ********************

        System.out.println("Azure locations:");
        //getLocationsComputeService("azurecompute", azureAccountName, azureAccountKey);
        System.out.println();

        // ******************** Google Compute Engine provider ********************

        System.out.println("Google Cloud locations:");
        getLocationsComputeService("google-compute-engine", gcloudClientEmail, gcloudPrivateKey);
        System.out.println();
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


    /**
     * Get available locations for ComputeService
     */
    private static void getLocationsComputeService(String provider, String identity, String credential) {
        // Init
        ComputeServiceContext context = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential)
                .buildView(ComputeServiceContext.class);

        // Instantiate a BlobStore
        ComputeService client = context.getComputeService();

        // Get available locations
        for (Location pLocation : client.listAssignableLocations()) {
            System.out.println("  - " + pLocation.getId());
        }

        // Disconnect
        context.close();
    }
}
