package br.com.fiap.totem_express.presentation.user;

import br.com.fiap.totem_express.application.user.output.DefaultUserView;
import br.com.fiap.totem_express.presentation.errors.BadRequestError;
import br.com.fiap.totem_express.presentation.user.requests.CreateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuarios", description = "API de Usuários")
public interface UserDocumentation {
    @Operation(summary = "Criar Usuário", description = "Cadastra um novo usuário (comum) no sistema.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Retorna o identificador do usuário e o nome",
                    content = { @Content(schema = @Schema(implementation = DefaultUserView.class),mediaType = "application/json")
            }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Retorna os erros de validação ao criar o usuário",
                    content = {@Content(schema = @Schema(implementation = BadRequestError.class))}
            )
    })
    ResponseEntity<?> create(CreateUserRequest request);

    @Operation(summary = "Listar Usuário", description = "Retorna o usuário cadastrado no sistema pelo CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DefaultUserView.class), mediaType = "application/json")),
            @ApiResponse(
                    responseCode = "400",
                    description = "Quando não há usuário cadastrado no sistema para aquele CPF"
            )
    })
    ResponseEntity<?> find(String document);
}
