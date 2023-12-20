package net.brujulaweb.repository.user;

import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.entities.user.UserStatus;
import domain.brujulaweb.repository.UserRepository;
import net.brujulaweb.repository.DBRepository;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.sql.DataSource;

import static domain.brujulaweb.util.DateUtils.fromTimestamp;

public class UserDBRepository extends DBRepository<User> implements UserRepository {

    public static final String INSERT_USER = "INSERT INTO user " +
            "(email,password, status) VALUES " +
            "(?,?,?)";

    public static final String GET_USER_BY_MAIL = "SELECT * FROM user " +
            "WHERE UPPER(email) = UPPER(?);";

    public static final String UPDATE_USER = "UPDATE user SET " +
            "status = ? , lockout_count = ?, lockout_date = ?, last_login = ? " +
            "WHERE id = ?";

    public UserDBRepository(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public Integer signup(String email, String password, String status) {
        return executeInsert(INSERT_USER,
                email,
                password,
                status);
    }

    @Override
    public User findByEmail(String email) {
        return executeSelect(GET_USER_BY_MAIL, this::handler, email);
    }

    @Override
    public void update(Integer userId, String status, int lookout_count, ZonedDateTime lookout_date, ZonedDateTime login_date) {
        executeUpdate(UPDATE_USER,
                status,
                lookout_count,
                lookout_date,
                login_date,
                userId);
    }

    private User handler(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return User.builder()
                    .userId(resultSet.getInt("id"))
                    .email(resultSet.getString("email"))
                    .lastLogin(fromTimestamp(resultSet.getTimestamp("last_login")))
                    .password(resultSet.getString("password"))
                    .lockoutDate(fromTimestamp(resultSet.getTimestamp("lockout_date")))
                    .lockoutCount(resultSet.getInt("lockout_count"))
                    .status(UserStatus.lookup(resultSet.getString("status")))
                    .build();
        }
        return null;
    }




}
