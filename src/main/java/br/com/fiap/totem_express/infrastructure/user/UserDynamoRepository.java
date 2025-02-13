package br.com.fiap.totem_express.infrastructure.user;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDynamoRepository implements UserRepository<Long> {

    private final DynamoDbTable<UserEntity> table;

    public UserDynamoRepository(DynamoDbClient client) {
        final var enhanced = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();
        this.table = enhanced.table("Users", TableSchema.fromBean(UserEntity.class));
    }

    @Override
    public boolean existsByEmailOrCpf(String email, String cpf) {
        final var response = table.scan(
                ScanEnhancedRequest
                        .builder()
                        .filterExpression(
                            Expression
                                    .builder()
                                    .expression("email = :email OR cpf = :cpf")
                                    .expressionValues(Map.of(
                                            ":email", AttributeValue.fromS(email),
                                            ":cpf", AttributeValue.fromS(cpf)
                                    ))
                                    .build()
                        )
                        .limit(1)
                        .build()
        );

        return response.items().stream().findFirst().isPresent();
    }

    @Override
    public Optional<UserEntity> findByCpf(String cpf) {
        final var query = ScanEnhancedRequest
                .builder()
                .filterExpression(
                        Expression
                                .builder()
                                .expression("cpf = :cpf")
                                .expressionValues(Map.of(
                                        ":cpf", AttributeValue.fromS(cpf)
                                ))
                                .build()
                )
                .limit(1)
                .build();

        return table
                .scan(query)
                .items()
                .stream()
                .findFirst();
    }

    @Override
    public UserEntity save(UserEntity user) {
        table.putItem(user);
        return user;
    }

    @Override
    public List<UserEntity> findAll() {
        return table.scan().items().stream().toList();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        final var user = table.getItem(request -> request.key(it -> it.sortValue(id.toString())));
        return Optional.ofNullable(user);
    }
}
