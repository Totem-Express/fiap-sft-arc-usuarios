package br.com.fiap.totem_express.application.user;

import br.com.fiap.totem_express.application.user.input.NewUserInput;
import br.com.fiap.totem_express.application.user.output.DefaultUserView;

public interface CreateUserUseCase {
    DefaultUserView execute(NewUserInput input);
}
