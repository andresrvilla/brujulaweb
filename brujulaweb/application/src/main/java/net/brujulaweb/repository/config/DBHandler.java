package net.brujulaweb.repository.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DBHandler {

    private static HikariDataSource ds;

    static {

        HikariConfig config = new HikariConfig();
        //config.setJdbcUrl("jdbc:mysql://localhost:3306/brujula.dev");
        config.setJdbcUrl(Config.get("db.jdbcUrl"));
        config.setUsername(Config.get("db.user"));
        config.setPassword(Config.get("db.password"));
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(Config.getInt("db.maximumPoolSize"));
        config.setConnectionTimeout(Config.getInt("db.connectionTimeout"));
        config.setLeakDetectionThreshold(300000);

        ds = new HikariDataSource(config);
    }

    public static DataSource getDataSource(){
        return ds;
    }


}
