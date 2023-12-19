package domain.brujulaweb.repository;

import domain.brujulaweb.entities.user.User;

import java.util.Optional;

public interface UserRepository {

    String signup (String email, String password, String status);

    User findByEmail (String email);
}
