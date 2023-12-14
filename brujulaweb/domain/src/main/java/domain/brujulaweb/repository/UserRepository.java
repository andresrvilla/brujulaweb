package domain.brujulaweb.repository;

import domain.brujulaweb.entities.user.User;

import java.util.Optional;

public interface UserRepository {

    User signup (String email, String password);

    Optional<User> findByEmail (String email);
}
