package br.com.fiap.totem_express.presentation.user;

import br.com.fiap.totem_express.application.user.CreateUserUseCase;
import br.com.fiap.totem_express.application.user.RetrieveUserUseCase;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.application.user.impl.CreateUserUseCaseImpl;
import br.com.fiap.totem_express.application.user.impl.RetrieveUserUseCaseImpl;
import br.com.fiap.totem_express.infrastructure.user.UserDynamoRepository;
import br.com.fiap.totem_express.infrastructure.user.UserGatewayImpl;
import br.com.fiap.totem_express.infrastructure.user.UserRepository;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.identity.spi.AwsCredentialsIdentity;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class UserConfiguration {

    @Bean
    public DynamoDbClient dynamoDbClient(
            @Value("${dynamo.endpoint}") String endpoint,
            @Value("${dynamo.accessKey}") String accessKey,
            @Value("${dynamo.secret}") String secret
    ){
        final var credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secret));

        return DynamoDbClient
            .builder()
            .endpointOverride(URI.create(endpoint))
            .credentialsProvider(
                    credentialsProvider
            )
            .build();
    }

    @Bean
    public UserRepository userRepository(DynamoDbClient client){
        return new UserDynamoRepository(client);
    }

    @Bean
    public UserGateway userGateway(UserRepository repository){
        return new UserGatewayImpl(repository);
    }

    @Bean
    public RetrieveUserUseCase retrieveUserUseCase(UserGateway gateway){
        return new RetrieveUserUseCaseImpl(gateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway gateway){
        return new CreateUserUseCaseImpl(gateway);
    }
}
