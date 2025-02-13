package br.com.fiap.totem_express.presentation.user;

import br.com.fiap.totem_express.application.user.CreateUserUseCase;
import br.com.fiap.totem_express.application.user.RetrieveUserUseCase;
import br.com.fiap.totem_express.infrastructure.user.UserDynamoRepository;
import br.com.fiap.totem_express.presentation.user.requests.CreateUserRequest;
import br.com.fiap.totem_express.presentation.user.validators.UniqueUserValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController implements UserDocumentation {

    private final UserDynamoRepository repository;
    private final RetrieveUserUseCase retrieveUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UniqueUserValidator uniqueUserValidator;

    public UserController(UserDynamoRepository repository, RetrieveUserUseCase retrieveUserUseCase, CreateUserUseCase createUserUseCase, UniqueUserValidator uniqueUserValidator) {
        this.repository = repository;
        this.retrieveUserUseCase = retrieveUserUseCase;
        this.createUserUseCase = createUserUseCase;
        this.uniqueUserValidator = uniqueUserValidator;
    }

    @InitBinder("createUserRequest")
    public void init (WebDataBinder it){
        it.addValidators(uniqueUserValidator);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid CreateUserRequest request){
        final var view = createUserUseCase.execute(request);

        return ResponseEntity.ok(view);
    }

    @GetMapping
    public ResponseEntity find(@RequestParam("document") String document){
        return retrieveUserUseCase
                .execute(document)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity xpto(@PathVariable Long id){
        return repository.findById(id).map(ResponseEntity::ok).orElseGet(
                () -> ResponseEntity.notFound().build()
        );
    }
}
