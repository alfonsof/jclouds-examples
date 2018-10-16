# Apache jclouds BlobStore Download Java example

This folder contains a Java application example that handles BlobStore containers using [Apache jclouds](https://jclouds.apache.org/), the Java Multi-Cloud Toolkit, on:

* AWS (Amazon Web Services)
* Microsoft Azure
* Google Cloud Platform (GCP)

It allows to download a blob from a BlobStore container to a local file in several cloud providers:

* S3 bucket on AWS (Amazon Web Services)
* Blob Storage container on Microsoft Azure
* Cloud Storage bucket on Google Cloud Platform (GCP)

## Requirements

You must have:

* an [AWS (Amazon Web Services)](http://aws.amazon.com/) account.
* a [Microsoft Azure](https://azure.microsoft.com/) subscription.
* an Azure storage account.
* a [Google Cloud Platform (GCP)](http://cloud.google.com/) account.

* The code was written for Java 8 and Apache jclouds 2.x.

## Using the code

* Configure your AWS access keys.

  **Important:** For security, it is strongly recommend that you use IAM users instead of the root account for AWS access.

  You must get the created:

  * `AWS ACCESS KEY ID`
  * `AWS SECRET ACCESS KEY`

* Configure your Azure access.

  You must create an Azure AD service principal in order to enable application to connect resources into Azure. The service principal grants your application to manage resources in your Azure subscription.

  You can create a service principal and generate this file using Azure CLI 2.0 or using the Azure cloud shell.

  * Make sure you select your subscription by:

    ```bash
    az account set --subscription <name or id>
    ```

    and you have the privileges to create service principals.

  * Execute the following command for creating the service principal and the authentication file:
  
    ```bash
    az ad sp create-for-rbac --sdk-auth > my.azureauth
    ```
  
* Configure your Azure storage account.

  An Azure storage account provides a unique namespace to store and access your Azure Storage data objects.
  
  There are two types of storage accounts:
  
  * A general-purpose storage account gives you access to Azure Storage services such as Tables, Queues, Files, Blobs and Azure virtual machine disks under a single account.

  * A Blob storage account is a specialized storage account for storing your unstructured data as blobs (objects) in Azure Storage.
    Blob storage accounts are similar to a existing general-purpose storage accounts and share all the great durability, availability,
    scalability, and performance features that you use today including 100% API consistency for block blobs and append blobs.

    For applications requiring only block or append blob storage, it is recommend using Blob storage accounts.

    Blob storage accounts expose the Access Tier attribute which can be specified during account creation and modified later as needed.

  An storage account can content containers and every container can content blobs.

  ```bash
  Storage Account
              ├── Container_1/
              │   ├── Blob_1_1/
              │   └── Blob_1_2/
              │
              └── Container_2/
                  ├── Blob_2_1/
                  ├── Blob_2_2/
                  └── Blob_2_3/
  ```

  Create a storage account:
  
  1. Sign in to the Azure portal.
  2. Select the "Storage accounts" option. On the Storage Accounts window that appears, choose Add.
  3. Enter a name for your storage account.
  4. Specify the deployment model to be used: Resource Manager or Classic. Select Resource Manager deployment model.
  5. Select the type of storage account: General purpose or Blob storage. Select General purpose.
  6. Select the geographic location for your storage account. 
  7. Select the replication option for the storage account: LRS, GRS, RA-GRS, or ZRS. Set Replication to Locally Redundant storage (LRS).
  8. Select the subscription in which you want to create the new storage account.
  9. Specify a new resource group or select an existing resource group. 
  10. Click Create to create the storage account.

  You can find your storage account's connection strings in the Azure portal:
  
    1. Navigate to "Storage Accounts".
    2. Select your storage account.
    3. You can see your connection strings and get your account name and account key.

    ```bash
    DefaultEndpointsProtocol=https;AccountName=ACCOUNT_NAME;AccountKey=ACCOUNT_KEY;EndpointSuffix=core.windows.net
    ```

  You must get the created:

  * `AZURE ACCOUNT_NAME`
  * `AZURE ACCOUNT_KEY`

* Configure your Google Cloud access keys.

  Use the [Google Cloud Platform console](http://cloud.google.com/):

  * Go to the Google Cloud Project.

  * Prepare the credentials:
    * Create a Service account.

      For example:

      ```bash
      Name: gcloud-java-examples
      Role: Owner
      Email: gcloud-java-examples@gcloud-java-examples.iam.gserviceaccount.com
      ```

    * Create a key as a JSON file and download it.

    * Add the Service accounts id (Ex.: gcloud-java-examples@gcloud-java-examples.iam.gserviceaccount.com) as a member of the project in the IAM.

  You must get the created:

  * `GOOGLE CLOUD CLIENT EMAIL`
  * `GOOGLE CLOUD PRIVATE KEY`

* We store the credentials for every cloud providers in a properties file (`app.properties`). The file content is:

  ```bash
  # AWS
  awsaccesskeyid=<AWS_ACCESS_KEY>
  awssecretkey=<AWS_SECRET_KEY>
  # Azure
  azure_account_name=<AZURE_ACCOUNT_NAME>
  azure_account_key=<AZURE_ACCOUNT_KEY>
  # Google Cloud
  gcloud_client_email=<GOOGLE_CLOUD_CLIENT_EMAIL>
  gcloud_private_key=<GOOGLE_CLOUD_PRIVATE_KEY>
  ```

* Run the code.

  You must provide 3 parameters:
  
  * `<CONTAINER_NAME>`  = Container name
  * `<BLOB_NAME>`       = Blob name in the container
  * `<LOCAL_FILE_NAME>` = Local file name

  Run application:

  ```bash
  java -jar jcloudsblobstoredownload.jar container-name blob-name local-file
  ```

* Test the application.

  You should see the local file created from the BlobStore containers:

  * S3 bucket on AWS (Amazon Web Services)
  * Blob Storage container on Microsoft Azure
  * Cloud Storage bucket on Google Cloud Platform (GCP)
