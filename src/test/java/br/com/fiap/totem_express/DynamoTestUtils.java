package br.com.fiap.totem_express;

import br.com.fiap.totem_express.infrastructure.user.UserEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.function.Consumer;

@Service
public class DynamoTestUtils {

    private final DynamoDbClient client;

    public DynamoTestUtils(DynamoDbClient client) {
        this.client = client;
    }

    public void clean(){
        final var enhanced = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();
        final var table = enhanced.table("Users", TableSchema.fromBean(UserEntity.class));

        Consumer<UserEntity> handleDeleteItem = (user) ->
            table.deleteItem(Key.builder().partitionValue(user.getId()).build());


        System.out.println("Start deleting items...");

        table
                .scan()
                .items()
                .stream()
                .peek(
                        (item) -> System.out.println("Deleting user %s with id %s".formatted(item.getEmail(), item.getId()))
                )
                .parallel()
                .forEach(handleDeleteItem);
    }
}
