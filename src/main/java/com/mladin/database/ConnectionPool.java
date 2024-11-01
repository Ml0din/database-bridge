package com.mladin.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

public class ConnectionPool {
    protected DatabaseSection databaseSection;
    protected HikariDataSource hikariDataSource;
    public ConnectionPool(DatabaseSection databaseSection) {
        this.databaseSection = databaseSection;
        init();
    }

    private void init() {
        this.hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:mysql://" + databaseSection.getDatabaseBridge().getHost() + ":" + databaseSection.getDatabaseBridge().getPort() + "/" + databaseSection.getName());
        hikariDataSource.setUsername(databaseSection.getDatabaseBridge().getUsername());
        hikariDataSource.setPassword(databaseSection.getDatabaseBridge().getPassword());
    }

    public Connection getConnection() {
        Connection result;
        try {
            result = this.hikariDataSource.getConnection();
            return result;
        }catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Severe database error. Can't get connection. Closing server.");
       }
    }

    public void close() {
        if(hikariDataSource != null) {
            if(!hikariDataSource.isClosed()) {
                hikariDataSource.close();
            }
        }
    }
}
