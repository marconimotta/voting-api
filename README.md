# sicredi-test-java

API [Spring Boot](http://projects.spring.io/spring-boot/).

## Description

API to creation agenda's and open sessions to vote. Have integration with CPF validation using [FeignClient](https://cloud.spring.io/spring-cloud-openfeign/reference/html/). The solution use Spring-boot, Maven, Junit and MongoDB to NoSQL Database.

## Requirements

For building and running the application you need:

- [JDK 22](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html)
- [Maven ](https://maven.apache.org/guides/introduction/introduction-to-repositories.html)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.sicredi.voting.VotingApiApplication` class from your IDE.
Or
Export and Create Jar File from this Spring boot and execute in your environment like docker, eks, or other service.


## Connecting to MongoDB

There is already a connection to the MongoDB cluster online, just change the application.yaml settings to your preferred settings.

```shell
spring:
  data:
    mongodb:
      uri: <your_uri>
```
## Swagger ui and api-docs

### Swagger api-docs
- After configuring the local environment, you can open swagger-api-docs through the url: http://localhost:<your_port>/api-docs

### Swagger ui
- After configuring the local environment, you can open swagger-ui through the url: http://localhost:<your_port>/swagger-ui.html

## Questions

Have questions? Send-me e-mail: marconi.motta@hotmail.com
