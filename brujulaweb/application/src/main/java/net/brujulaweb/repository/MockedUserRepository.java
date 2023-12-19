package net.brujulaweb.repository;

import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.entities.user.UserStatus;
import domain.brujulaweb.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class MockedUserRepository implements UserRepository {
    private List<User> users;

    public MockedUserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public String signup(String email, String password, String status) {
        String userId = UUID.randomUUID().toString();
        User user = User.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .status(UserStatus.lookup(status))
                .build();
        users.add(user);
        return user.getUserId();

    }

    @Override
    public User findByEmail(String email) {
        Predicate<User> query = user -> user.getEmail().equalsIgnoreCase(email);
        return users.stream().filter(query).findFirst().orElse(null);
    }
}
