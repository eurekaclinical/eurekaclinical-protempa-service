# Eurekaclinical! Protempa Service
[Atlanta Clinical and Translational Science Institute (ACTSI)](http://www.actsi.org), [Emory University](http://www.emory.edu), Atlanta, GA

## What does it do?
It provides backend services for managing phenotypes, cohorts and running phenotyping jobs.

## Version 3.0 development series
Latest release: [![Latest release](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/eurekaclinical-protempa-service/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.eurekaclinical/eurekaclinical-protempa-service)

Version 3 will refactor the `eureka-services` and `eureka-protempa-etl` modules to migrate from a layer architecture to a microservice architecture `eurekaclinical-analytics-service` and `eurekaclinical-protempa-service`. It also will improve performance.

## Version history
### Version 2.5.2
As compared with version 1, we refactored and renamed some of the REST APIs.

### Version 1.9
The initial version of the Eureka backend is implemented as a layer architecture, in which the webapp makes all REST calls to `eureka-services`, and `eureka-services` forwards some calls to `eureka-protempa-etl`.

## Build requirements
* [Oracle Java JDK 8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Maven 3.2.5 or greater](https://maven.apache.org)

## Runtime requirements
* [Oracle Java JRE 8](http://www.oracle.com/technetwork/java/javase/overview/index.html)
* [Tomcat 7](https://tomcat.apache.org)
* Also running
  * The [eurekaclinical-analytics-webapp](https://github.com/eurekaclinical/eurekaclinical-analytics-webapp) war
  * The [eurekaclinical-analytics-service](https://github.com/eurekaclinical/eurekaclinical-analytics-service) war
  * The [eurekaclinical-protempa-service](https://github.com/eurekaclinical/eurekaclinical-protempa-service) war
  * The [eurekaclinical-user-webapp](https://github.com/eurekaclinical/eurekaclinical-user-webapp) war
  * The [eurekaclinical-user-service](https://github.com/eurekaclinical/eurekaclinical-user-service) war
  * The [cas-server](https://github.com/eurekaclinical/cas) war
  
## REST APIs
Are all accessed via the `eureka-services` module and are otherwise internal to Eureka! Clinical Analytics.
  
## Building it
See the parent project's [README.md](https://github.com/eurekaclinical/eureka/blob/master/README.md).

## Performing system tests
See the parent project's [README.md](https://github.com/eurekaclinical/eureka/blob/master/README.md).

## Installation
### Configuration
This webapp is configured using a properties file located at `/etc/eureka/application.properties`. It supports the following properties:
* `cas.url`: https://hostname.of.casserver:port/cas-server
* `eureka.etl.url`: URL of the server running the backend; default is https://localhost:8443/eurekaclinical-protempa-service.
* `eureka.etl.threadpool.size`: the number of threads in the ETL threadpool; default is 4.
* `eureka.etl.callbackserver`: URL of the server running the backend; default is https://localhost:8443.

A Tomcat restart is required to detect any changes to the configuration file.

### WAR installation
1) Stop Tomcat.
2) Remove any old copies of the unpacked war from Tomcat's webapps directory.
3) Copy the warfile into the Tomcat webapps directory, renaming it to remove the version if necessary. For example, rename `eurekaclinical-protempa-service-1.0.war` to `eurekaclinical-protempa-service.war`.
4) Start Tomcat.

## Maven dependency
```
<dependency>
    <groupId>org.eurekaclinical</groupId>
    <artifactId>eurekaclinical-protempa-service</artifactId>
    <version>version</version>
</dependency>
```

## Developer documentation
* [Javadoc for latest development release](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-protempa-service) [![Javadocs](http://javadoc.io/badge/org.eurekaclinical/eurekaclinical-protempa-service.svg)](http://javadoc.io/doc/org.eurekaclinical/eurekaclinical-protempa-service)

## Getting help
Feel free to contact us at help@eurekaclinical.org.
