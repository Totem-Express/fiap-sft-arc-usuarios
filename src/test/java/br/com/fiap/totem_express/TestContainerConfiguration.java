package br.com.fiap.totem_express;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainerConfiguration {

    private static final DockerImageName LOCALSTACK_IMAGE_NAME = DockerImageName.parse("localstack/localstack:2.1.0");

    @Bean
    public LocalStackContainer buildLocalStackWithDynamo(){
        final var container = new LocalStackContainer(LOCALSTACK_IMAGE_NAME).withServices(LocalStackContainer.Service.DYNAMODB);
        container.start();
        return container;
    }

}
