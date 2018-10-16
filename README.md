# Java examples using Apache jclouds

This repo contains Java code examples using Apache jclouds, the Java Multi-Cloud Toolkit, on:

* AWS (Amazon Web Services)
* Microsoft Azure
* Google Cloud Platform (GCP)

These examples show how to use Java 8 and Apache jclouds in order to manage services on public cloud providers like: AWS, Azure and Google Cloud.

Apache jclouds is an open source multi-cloud toolkit for the Java platform that gives you the freedom to create applications that are portable across clouds while giving you full control to use cloud-specific features.

[Apache jclouds](https://jclouds.apache.org/).

Apache jclouds allows Java developers to write software that makes use of:

* Amazon services like EC2 and S3.
* Azure services like Virtual Machines and Blob storage.
* Google Cloud services like Compute Engine and Cloud Storage.

## Quick start

You must have:

* an [AWS (Amazon Web Services)](http://aws.amazon.com/) account.
* a [Microsoft Azure](https://azure.microsoft.com/) subscription.
* an Azure storage account for the Azure Blob Storage examples.
* a [Google Cloud Platform (GCP)](http://cloud.google.com/) account.

The code for the samples is contained in individual folders on this repository.

For instructions on running the code, please consult the README in each folder.

This is the list of examples:

**Compute - CompureService:**

* [jcloudscomputeservicegetlocations](/jcloudscomputeservicegetlocations) - Apache jclouds ComputeService Get Locations: Example of how to handle CompureService and get the available locations for CompureService.

**Storage - BlobStore:**

* [jcloudsblobstoregetlocations](/jcloudsblobstoregetlocations) - Apache jclouds BlobStore Get Locations: Example of how to handle BlobStore containers and get the available locations for BlobStore containers.
* [jcloudsblobstorecreate](/jcloudsblobstorecreate) - Apache jclouds BlobStore Create: Example of how to handle BlobStore containers and create a new BlobStore container.
* [jcloudsblobstoredelete](/jcloudsblobstoredelete) - Apache jclouds BlobStore Delete: Example of how to handle BlobStore containers and delete a BlobStore container.
* [jcloudsblobstorelist](/jcloudsblobstorelist) - Apache jclouds BlobStore List: Example of how to handle BlobStore containers and list the blobs in a BlobStore container.
* [jcloudsblobstorelistall](/jcloudsblobstorelistall) - Apache jclouds BlobStore List: Example of how to handle BlobStore containers and list all the containers in BlobStore.
* [jcloudsblobstoreupload](/jcloudsblobstoreupload) - Apache jclouds BlobStore Upload: Example of how to handle BlobStore containers and upload a local file to a BlobStore container.
* [jcloudsblobstoredownload](/jcloudsblobstoredownload) - Apache jclouds BlobStore Download: Example of how to handle BlobStore containers and download a blob from a BlobStore container to a local file.
* [jcloudsblobstoredeleteblob](/jcloudsblobstoredeleteblob) -  Apache jclouds BlobStore Delete Object: Example of how to handle BlobStore containers and delete a blob in a BlobStore container.

## License

This code is released under the MIT License. See LICENSE file.
