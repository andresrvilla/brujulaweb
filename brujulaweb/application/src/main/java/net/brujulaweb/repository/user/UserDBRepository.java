package net.brujulaweb.repository.user;

import domain.brujulaweb.entities.user.User;
import domain.brujulaweb.repository.UserRepository;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;

public class UserDBRepository implements UserRepository {

    public static final String INSERT_USER = "INSERT INTO user " +
            "(id,email,password, status) VALUES " +
            "(?,?,?,?)";


    public static final String GET_USER_BY_MAIL = "SELECT * FROM user " +
            "WHERE UPPER(email) = UPPER(?);";

    public static final String UPDATE_USER = "";

    public static final String DELETE_USER = "";
    private final QueryRunner queryRunner;

    private final DataSource dataSource;

    public UserDBRepository(DataSource dataSource) {
        this.queryRunner = new QueryRunner();
        this.dataSource = dataSource;
    }

    @Override
    public String signup(String email, String password, String status) {
        try {
            UUID uuid = UUID.randomUUID();

            queryRunner.insert(dataSource.getConnection(),
                    INSERT_USER,
                    this::idHandler,
                    uuid.toString(),
                    email,
                    password,
                    status);

            return uuid.toString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return queryRunner.query(dataSource.getConnection(),
                    GET_USER_BY_MAIL,
                    this::handler,
                    email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User handler(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return User.builder()
                    .userId(resultSet.getString("id"))
                    .email(resultSet.getString("email"))
                    .lastLogin(resultSet.getDate("last_login"))
                    .password(resultSet.getString("password"))
                    .lockoutDate(resultSet.getDate("lockout_date"))
                    .lockoutCount(resultSet.getInt("lockout_count"))
                    .build();
        }
        return null;
    }

    private String idHandler(ResultSet resultSet) throws SQLException {
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }



}
