package br.com.fiap.totem_express.presentation.errors;

import java.util.List;

public record BadRequestError(List<FieldError> errors) {
}
