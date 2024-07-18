# 5gLa API

## Description

This service provides the integration of multiple sensors with the 5gLa platform.
It is part of the 5GLA project, which is funded by the German Federal Ministry of Transport and Digital Infrastructure (
BMVI).The website of the project is https://www.5gla.de/, you can find all additional information there. If you are
interested in the source code,you can find it on
GitHub: https://github.com/vitrum-connect/5gla-sensor-integration-services

## Build the project

To build the project and to resolve the dependencies from GitHub Packages you need to create a personal access token and
place it in the file `~/.m2/settings.xml`:

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
