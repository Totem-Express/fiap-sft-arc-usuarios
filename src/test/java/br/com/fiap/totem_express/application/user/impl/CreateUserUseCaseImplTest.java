package br.com.fiap.totem_express.application.user.impl;

import br.com.fiap.totem_express.DynamoTestConfiguration;
import br.com.fiap.totem_express.DynamoTestUtils;
import br.com.fiap.totem_express.TestContainerConfiguration;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.user.User;
import br.com.fiap.totem_express.infrastructure.user.UserEntity;
import br.com.fiap.totem_express.presentation.user.requests.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.fiap.totem_express.domain.user.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@Import({ TestContainerConfiguration.class, DynamoTestConfiguration.class})
class CreateUserUseCaseImplTest {

    private UserGateway gateway;

    private CreateUserUseCaseImpl createUserUseCase;

    @Autowired
    private DynamoTestUtils testUtils;

    @BeforeEach
    void setUp() {
        gateway = mock(UserGateway.class);
        createUserUseCase = new CreateUserUseCaseImpl(gateway);
        testUtils.clean();
    }

    @Test
    void should_create_user_when_user_does_not_exist() {
        var name = "Rose DeWitt";
        var email = "rosewitt@email.com";
        var cpf = "114.974.750-15";

        var input = new CreateUserRequest(name, email, cpf);
        var createdUser = new User(UUID.randomUUID().toString(), name, email, cpf, LocalDateTime.now(), USER);

        when(gateway.existsByEmailOrCPF(input.email(), input.cpf())).thenReturn(false);
        when(gateway.create(any(User.class))).thenReturn(new UserEntity(createdUser).toDomain());

        var result = createUserUseCase.execute(input);

        assertThat(result.id()).isEqualTo(createdUser.getId());
        assertThat(result.name()).isEqualTo(createdUser.getName());
    }

    @Test
    void should_throw_exception_when_user_already_exists() {
        var input = new CreateUserRequest("Rose DeWitt", "rosewitt@email.com", "114.974.750-15");

        when(gateway.existsByEmailOrCPF(input.email(), input.cpf())).thenReturn(true);

        assertThatThrownBy(() -> createUserUseCase.execute(input))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User already exists");
    }
}