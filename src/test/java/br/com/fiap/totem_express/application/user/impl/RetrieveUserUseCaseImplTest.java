package br.com.fiap.totem_express.application.user.impl;

import br.com.fiap.totem_express.DynamoTestConfiguration;
import br.com.fiap.totem_express.DynamoTestUtils;
import br.com.fiap.totem_express.TestContainerConfiguration;
import br.com.fiap.totem_express.application.user.RetrieveUserUseCase;
import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.application.user.output.DefaultUserView;
import br.com.fiap.totem_express.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({ TestContainerConfiguration.class, DynamoTestConfiguration.class})
class RetrieveUserUseCaseImplTest {

    @Autowired
    private RetrieveUserUseCase retrieveUserUseCase;

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private DynamoTestUtils dynamoTestUtils;

    @BeforeEach
    public void setup(){
        dynamoTestUtils.clean();
    }

    @Test
    void execute_should_return_default_user_view_when_user_exists() {
        User hilaryOBrian = userGateway.create(new User("Hilary OBrian", "hilary@hotmail.com", "235.540.770-38"));
        var result = retrieveUserUseCase.execute("235.540.770-38");

        assertThat(result).isPresent();
        DefaultUserView defaultUserView = result.get();
        assertThat(defaultUserView.id()).isEqualTo(hilaryOBrian.getId());
        assertThat(defaultUserView.name()).isEqualTo(hilaryOBrian.getName());
    }

    @Test
    void execute_should_return_empty_optional_when_user_does_not_exist() {
        var result = retrieveUserUseCase.execute("98765432100");

        assertThat(result).isEmpty();
    }
}