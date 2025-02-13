package br.com.fiap.totem_express.presentation.user.validators;

import br.com.fiap.totem_express.infrastructure.user.UserRepository;
import br.com.fiap.totem_express.presentation.user.requests.CreateUserRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UniqueUserValidator implements Validator {

    private final UserRepository repository;

    public UniqueUserValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateUserRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        final var current = (CreateUserRequest) target;

        final var alreadyExistsUser = repository.existsByEmailOrCpf(current.email(), current.cpf());

        if (alreadyExistsUser){
            errors.reject("user.already.exists", "Já existe um usuário cadastrado com este email ou cpf!");
        }
    }
}
