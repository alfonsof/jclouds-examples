# Apache jclouds BlobStore List Java example

This folder contains a Java application example that handles BlobStore containers using [Apache jclouds](https://jclouds.apache.org/), the Java Multi-Cloud Toolkit, on:

* AWS (Amazon Web Services)
* Microsoft Azure
* Google Cloud Platform (GCP)

It allows to list Blobs in a BlobStore container in several cloud providers:

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

  An Azure storage account contains all of your Azure Storage data objects: blobs, file shares, queues, tables, and disks. The storage account provides a unique namespace for your Azure Storage data that's accessible from anywhere in the world over HTTP or HTTPS. Data in your storage account is durable and highly available, secure, and massively scalable.
  
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

  Create a storage account using the Azure portal:
  
  1. Select the `Storage account` option and choose `Create`.
  2. Select the `Subscription` in which you want to create the new storage account.
  3. Select the `Resource Group` for your storage account.
  4. Enter a `name` for your storage account.
  5. Select the `Region` for your storage account. 
  6. Select the `Performance` to be used.
  7. Select the `Redundancy` to be used.
  8. Click `Create` to create the storage account.

  A connection string includes the authentication information required for your application to access data in an Azure Storage account at runtime.

  Your application needs to access the connection string at runtime to authorize requests made to Azure Storage.

  You can find your storage account's connection strings in the Azure portal:
  
    1. Navigate to `Storage Account`.
    2. Select your storage account.
    3. Select `Access keys` and you can see your Storage account name, connection strings and account keys.

  The connection string looks like this:

    ```bash
    DefaultEndpointsProtocol=https;AccountName=<AZURE_ACCOUNT_NAME>;AccountKey=<AZURE_ACCOUNT_KEY>;EndpointSuffix=core.windows.net
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

  You must provide 1 parameter, replace the value of:
  
  * `<CONTAINER_NAME>` by Container name.

  Run application:

  ```bash
  java -jar jcloudsblobstorelist.jar <CONTAINER_NAME>
  ```

* Test the application.

  You should see the list of blobs stored in the BlobStore containers on:

  * S3 bucket on AWS (Amazon Web Services)
  * Blob Storage container on Microsoft Azure
  * Cloud Storage bucket on Google Cloud Platform (GCP)
