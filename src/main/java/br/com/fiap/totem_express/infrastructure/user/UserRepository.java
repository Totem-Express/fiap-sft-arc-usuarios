package br.com.fiap.totem_express.infrastructure.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository<T> {
    boolean existsByEmailOrCpf(String email, String cpf);
    Optional<UserEntity> findByCpf(String cpf);
    UserEntity save(UserEntity user);
    List<UserEntity> findAll();
    boolean existsById(T id);
    Optional<UserEntity> findById(T id);
}
