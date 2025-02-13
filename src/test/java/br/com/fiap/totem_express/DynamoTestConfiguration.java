package br.com.fiap.totem_express;


import br.com.fiap.totem_express.infrastructure.user.UserEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.testcontainers.containers.localstack.LocalStackContainer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@TestConfiguration(proxyBeanMethods = true)
public class DynamoTestConfiguration {

    @Bean
    @Primary
    public DynamoDbClient dynamoClient(LocalStackContainer localStack){
        final var credentials = StaticCredentialsProvider.create(AwsBasicCredentials.create("test", "test"));
        return DynamoDbClient
                .builder()
                .credentialsProvider(credentials)
                .endpointOverride(localStack.getEndpointOverride(LocalStackContainer.Service.DYNAMODB))
                .build();
    }

    @Bean
    public DynamoTestUtils dynamoTestUtils(DynamoDbClient client){
        return new DynamoTestUtils(client);
    }

    @EventListener
    @SuppressWarnings("unchecked")
    public void onApplicationReady(ApplicationReadyEvent event){
        final var ctx = event.getApplicationContext();
        final var client = (DynamoDbClient) ctx.getBean("dynamoClient");

        final var enhanced = DynamoDbEnhancedClient
                .builder()
                .dynamoDbClient(client)
                .build();

        final var schema = TableSchema.fromBean(UserEntity.class);

        enhanced.table("Users", schema).createTable();
    }
}
