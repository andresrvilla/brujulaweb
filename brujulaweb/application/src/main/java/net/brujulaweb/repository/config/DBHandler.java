package net.brujulaweb.repository.config;

public class DBHandler{
    private static HikariDataSource ds;
    static{

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/simpsons");
        config.setUsername("bart");
        config.setPassword("51mp50n");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
    }

    public static Connection getConn() throws SQLException {
        return ds.getConnection();
    }

