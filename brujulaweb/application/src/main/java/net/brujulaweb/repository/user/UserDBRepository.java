package net.brujulaweb.repository.user;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import domain.brujulaweb.entities.RowStatus;
import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.entities.user.UserStatus;
import domain.brujulaweb.repository.UserRepository;
import domain.brujulaweb.util.DateUtils;
import net.brujulaweb.repository.DBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Objects;

import static domain.brujulaweb.util.DateUtils.fromTimestamp;

@Singleton
public class UserDBRepository extends DBRepository<User> implements UserRepository {

    public static final String INSERT_USER = "INSERT INTO user " +
            "(email, password, association, creation_date, creation_user, modification_date, modification_user, status) VALUES " +
            "(?,?,?,?,?,?,?,?)";
    public static final String GET_USER_BY_MAIL = "SELECT * FROM user " +
            "WHERE UPPER(email) = UPPER(?) AND status = ?;";
    public static final String UPDATE_USER = "UPDATE user SET " +
            "status = ? , lockout_count = ?, lockout_date = ?, last_login = ? " +
            "WHERE id = ? AND status = ?";
    private static final Logger logger = LoggerFactory.getLogger(UserDBRepository.class);
    private static final int SIGNUP_USER = 0;
    private final Cache<String, User> userCache;

    @Inject
    public UserDBRepository(DataSource dataSource, Cache<String, User> userCache) {
        super(dataSource);
        this.userCache = userCache;
    }


    @Override
    public Integer signup(String email, String password, int association) {
        return executeInsert(INSERT_USER,
                email,
                password,
                association,
                DateUtils.now(),
                SIGNUP_USER,
                DateUtils.now(),
                SIGNUP_USER,
                RowStatus.ACTIVE.name());
    }

    @Override
    public User findByEmail(String email) {
        User user = userCache.getIfPresent(email);
        if (Objects.isNull(user)) {
            user = executeSelect(GET_USER_BY_MAIL, this::handler, email, RowStatus.ACTIVE.name());
            userCache.put(email, user);
            logger.info("Get user {} from database", email);
        }

        return user;
    }

    @Override
    public void updateSignup(String email, Integer userId, String status, int lookout_count, ZonedDateTime lookout_date, ZonedDateTime login_date) {
        executeUpdate(UPDATE_USER,
                status,
                lookout_count,
                lookout_date,
                login_date,
                userId,
                RowStatus.ACTIVE.name());
        userCache.invalidate(email);
    }

    private User handler(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            User result = User.builder()
                    .userId(resultSet.getInt("id"))
                    .email(resultSet.getString("email"))
                    .lastLogin(fromTimestamp(resultSet.getTimestamp("last_login")))
                    .password(resultSet.getString("password"))
                    .lockoutDate(fromTimestamp(resultSet.getTimestamp("lockout_date")))
                    .lockoutCount(resultSet.getInt("lockout_count"))
                    .status(UserStatus.lookup(resultSet.getString("status")))
                    .build();

            result.setAssociationId(resultSet.getInt("association"));
            return result;
        }
        return null;
    }


}
