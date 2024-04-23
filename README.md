# 5gLa API

## Description

This service provides the integration of multiple sensors with the 5gLa platform.
It is part of the 5GLA project, which is funded by the German Federal Ministry of Transport and Digital Infrastructure (
BMVI).The website of the project is https://www.5gla.de/, you can find all additional information there. If you are
interested in the source code,you can find it on
GitHub: https://github.com/vitrum-connect/5gla-sensor-integration-services

## Run the application within the IDE

To run the application you need to set multiple environment variables. The easiest way to do this is to use the
following template and replace the values with your own ones:

```
  CONTEXT_BROKER_URL=https://localhost:1026/;
  MICROSTREAM_STORAGE_DIRECTORY=/opt/application/.microstream;
  IMAGE_PATH_BASE_URL=http://localhost:8080/api/images/;
  API_KEY=___CHANGE_ME___;
  NOTIFICATION_URLS=https://localhost:5050/notify;
  SPRING_PROFILES_ACTIVE=maintenance;
  CORS_ALLOWED_ORIGINS=http://localhost:8080;
  CONTEXT_PATH=/api;
```

The following table describes the environment variables:

| Environment Variable          | Description                                             |
|-------------------------------|---------------------------------------------------------|
| CONTEXT_BROKER_URL            | The URL of the Orion Context Broker.                    |
| MICROSTREAM_STORAGE_DIRECTORY | The directory where the Microstream database is stored. |
| IMAGE_PATH_BASE_URL           | The base URL of the image path.                         |
| SENTEK_API_TOKEN              | The API token of the Sentek account.                    |
| API_KEY                       | The API key of the application.                         |
| NOTIFICATION_URLS             | The URL of the notification service.                    |
| SPRING_PROFILES_ACTIVE        | The active Spring profile.                              |
| CORS_ALLOWED_ORIGINS          | The allowed origins for CORS.                           |
| CONTEXT_PATH                  | The context path of the application.                    |

## Build the project

To build the project and to resolve the dependencies from GitHub Packages you need to create a personal access token and place it in the file `~/.m2/settings.xml`:

```xml

<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <activeProfiles>
        <activeProfile>github</activeProfile>
    </activeProfiles>

    <profiles>
        <profile>
            <id>github</id>
            <repositories>
                <repository>
                    <id>central</id>
                    <url>https://repo1.maven.org/maven2</url>
                </repository>
                <repository>
                    <id>vitrum-connect</id>
                    <name>GitHub Packages</name>
                    <url>https://maven.pkg.github.com/vitrum-connect/5gla-fiware-integration-layer</url>
                </repository>
            </repositories>
        </profile>
    </profiles>

    <servers>
        <server>
            <id>vitrum-connect</id>
            <username>###YOUR_USERNAME###</username>
            <password>###YOUR_ACCESS_TOKEN###</password>
        </server>
    </servers>
</settings>
```
