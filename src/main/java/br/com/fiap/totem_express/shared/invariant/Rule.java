package br.com.fiap.totem_express.shared.invariant;

import br.com.fiap.totem_express.shared.validators.CPFValidator;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

public record Rule<T>(Predicate<T> predicate, String message){

    public boolean validate(T target){
        return predicate.test(target);
    }

    public boolean notValid(T target){
        return !predicate.test(target);
    }

    public static <T> Rule<String> notBlank(String message){
        return new Rule<>(
                (value) -> value != null && value.length() > 0,
                message
        );
    }

    public static Rule<Collection> notEmpty(String message){
        return new Rule<>(
                (it) -> it.size() > 0,
                message
        );
    }

    public static <T> Rule<T> notNull(String message){
        return new Rule<>(Objects::nonNull, message);
    }

    public static Rule<BigDecimal> gt(BigDecimal target, String message){
        return new Rule<>(
                (value) -> value.compareTo(target) >= 1,
                message
        );
    }

    public static Rule<String> len(int length, String message) {
        return new Rule<>(
                (string) -> string.length() == length,
                message
        );
    }

    public static Rule<String> min(int min, String message) {
        return new Rule<>(
                (string) -> string.length() >= min,
                message
        );
    }

    public static Rule<String> max(int max, String message) {
        return new Rule<>(
                (string) -> string.length() <= max,
                message
        );
    }

    public static Rule<String> validCPF(String message){
        return new Rule<>(
                (cpf) -> CPFValidator.validate(cpf),
                message
        );
    }
}