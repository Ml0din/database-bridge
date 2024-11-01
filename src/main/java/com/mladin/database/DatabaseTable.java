package com.mladin.database;

public class DatabaseTable {
    protected DatabaseSection databaseSection;
    protected String name;

    public DatabaseTable(DatabaseSection databaseSection, String name) {
        this.databaseSection = databaseSection;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ConnectionPool getConnectionPool() {
        return this.databaseSection.getConnectionPool();
    }
}
