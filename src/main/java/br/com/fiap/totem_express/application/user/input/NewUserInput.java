package br.com.fiap.totem_express.application.user.input;

import br.com.fiap.totem_express.domain.user.User;

public interface NewUserInput {

    String name();

    String cpf();

    String email();

    User toDomain();
}
