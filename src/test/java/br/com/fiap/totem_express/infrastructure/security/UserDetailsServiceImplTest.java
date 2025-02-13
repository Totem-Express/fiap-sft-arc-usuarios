package br.com.fiap.totem_express.infrastructure.security;

import br.com.fiap.totem_express.application.user.UserGateway;
import br.com.fiap.totem_express.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.totem_express.domain.user.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {

    private UserGateway userGateway;

    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        userGateway = mock(UserGateway.class);
        userDetailsService = new UserDetailsServiceImpl(userGateway);
    }

    @Test
    void should_load_user_by_username() {
        String cpf = "114.974.750-15";
        String id = UUID.randomUUID().toString();
        User user = new User(id, "Gloria Maria", "gloriamaria@email.com", cpf, LocalDateTime.now(), USER);
        when(userGateway.findByCPF(cpf)).thenReturn(Optional.of(user));

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(cpf);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(cpf);
    }

    @Test
    void should_throw_exception_when_user_not_found_by_username() {
        String cpf = "114.974.750-15";
        when(userGateway.findByCPF(cpf)).thenReturn(Optional.empty());

        var exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(cpf));
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    void should_load_user_by_id() {
        String userId = UUID.randomUUID().toString();
        User user = new User(userId, "Gloria Maria", "gloriamaria@email.com", "114.974.750-15", LocalDateTime.now(), USER);
        when(userGateway.findById(userId)).thenReturn(Optional.of(user));

        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserById(userId);

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("114.974.750-15");
    }

    @Test
    void should_throw_exception_when_user_not_found_by_id() {
        String userId = UUID.randomUUID().toString();
        when(userGateway.findById(userId)).thenReturn(Optional.empty());

        var exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserById(userId));
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }
}