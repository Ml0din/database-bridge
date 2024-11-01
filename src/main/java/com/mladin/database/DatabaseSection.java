package com.mladin.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class DatabaseSection {
    protected String name;

    protected DatabaseBridge databaseBridge;
    protected DatabaseManager databaseManager;

    protected ConnectionPool connectionPool;

    protected HashMap<String, DatabaseTable> tables = new HashMap<>();

    public DatabaseSection(String name, DatabaseBridge databaseBridge, DatabaseManager databaseManager) {
        this.name = name;
        this.databaseBridge = databaseBridge;
        this.databaseManager = databaseManager;
        this.connectionPool = new ConnectionPool(this);

        init();
    }

    private void init() {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("Show tables");
            ResultSet resultSet = preparedStatement.executeQuery();

            int howManyTablesWereRegistered = 0;
            while (resultSet.next()) {
                String table = resultSet.getString(1).toLowerCase();
                tables.put(table, new DatabaseTable(this, table));
                howManyTablesWereRegistered++;
            }

            databaseBridge.getLogger().info("Scheme " + name + ": " + howManyTablesWereRegistered + " tables initialized.");

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public DatabaseTable getTable(String key) {
        return tables.get(key);
    }

    public String getName() {
        return this.name;
    }

    public ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    public DatabaseBridge getDatabaseBridge() {
        return this.databaseBridge;
    }
}
