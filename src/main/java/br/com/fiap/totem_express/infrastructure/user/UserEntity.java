package br.com.fiap.totem_express.infrastructure.user;

import br.com.fiap.totem_express.domain.user.Role;
import br.com.fiap.totem_express.domain.user.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDateTime;

@DynamoDbBean
public class UserEntity {

    private String id;

    private String name;

    private String email;

    private String cpf;

    @NotNull
    private Role role;

    @PastOrPresent
    private LocalDateTime createdAt;

    @Deprecated
    public UserEntity() {
    }

    public UserEntity(User user) {
        this.id = String.valueOf(user.getId());
        this.name = user.getName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt();
    }

    public User toDomain() {
        return new User(id, name, email, cpf, createdAt, role);
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = String.valueOf(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
