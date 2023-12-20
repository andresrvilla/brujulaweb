package domain.brujulaweb.repository;

import domain.brujulaweb.entities.user.User;

import java.time.ZonedDateTime;

public interface UserRepository {

    Integer signup (String email, String password, String status);

    User findByEmail (String email);

    void update(Integer userId, String status, int lookout_count, ZonedDateTime lookout_date, ZonedDateTime login_date);
}
