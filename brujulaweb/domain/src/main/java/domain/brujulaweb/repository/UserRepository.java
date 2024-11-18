package domain.brujulaweb.repository;

import domain.brujulaweb.entities.user.User;

import java.time.ZonedDateTime;

public interface UserRepository {

    Integer signup (String email, String password, int association);

    User findByEmail (String email);

    void updateSignup(String email, Integer userId, String status, int lookout_count, ZonedDateTime lookout_date, ZonedDateTime login_date);
}
