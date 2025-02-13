package br.com.fiap.totem_express.application.user;

import br.com.fiap.totem_express.application.user.output.DefaultUserView;

import java.util.Optional;

public interface RetrieveUserUseCase {
    Optional<DefaultUserView> execute(String cpf);
}
