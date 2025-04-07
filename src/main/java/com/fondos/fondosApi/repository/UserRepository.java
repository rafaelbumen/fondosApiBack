package com.fondos.fondosApi.repository;

import com.fondos.fondosApi.model.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {

    private final DynamoDbTable<User> userTable;

    public UserRepository(DynamoDbEnhancedClient enhancedClient) {
        this.userTable = enhancedClient.table("Users", TableSchema.fromBean(User.class));
    }

    @Override
    public User save(User user) {
        userTable.putItem(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userTable.getItem(r -> r.key(k -> k.partitionValue(id))));
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        userTable.scan().items().forEach(users::add);
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userTable.scan().items()
            .stream()
            .filter(user -> user.getEmail() != null && user.getEmail().equals(email))
            .findFirst();
    }
}
