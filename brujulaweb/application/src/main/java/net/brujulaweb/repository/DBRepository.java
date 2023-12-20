package net.brujulaweb.repository;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBRepository<T> {
    protected final QueryRunner queryRunner;

    public DBRepository(DataSource dataSource) {
        this.queryRunner = new QueryRunner(dataSource);
    }

    public Integer executeInsert(String cmd, Object... params) {
        Connection connection = null;
        try {
            connection = this.queryRunner.getDataSource().getConnection();
            return this.queryRunner.insert(connection, cmd, this::getId, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public T executeSelect(String cmd, ResultSetHandler<T> handler, Object... params) {
        Connection connection = null;
        try {
            connection = this.queryRunner.getDataSource().getConnection();
            T obj = queryRunner.query(connection,
                    cmd,
                    handler,
                    params);
            connection.close();
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void executeUpdate(String cmd, Object... params) {
        Connection connection = null;
        try {
            connection = this.queryRunner.getDataSource().getConnection();
            queryRunner.update(connection,
                    cmd,
                    params);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Integer getId(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return null;
    }
}
