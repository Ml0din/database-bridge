package com.mladin.database;

import java.util.HashMap;

public class DatabaseManager {
    protected DatabaseBridge databaseBridge;
    protected HashMap<String, DatabaseSection> schemes = new HashMap<>();

    public DatabaseManager(DatabaseBridge databaseBridge) {
        this.databaseBridge = databaseBridge;
    }

    public void addScheme(String scheme) {
        schemes.put(scheme, new DatabaseSection(scheme, databaseBridge, this));
        databaseBridge.getLogger().info("Added scheme " + scheme + ".");
    }

    public void removeScheme(String scheme) {
        schemes.get(scheme).getConnectionPool().close();
        schemes.remove(scheme);

        databaseBridge.getLogger().info("Closed connection pool for " + scheme + ".");
    }

    public void close() {
        for(String scheme : schemes.keySet()) {
            schemes.get(scheme).getConnectionPool().close();
            databaseBridge.getLogger().info("Closed connection pool for " + scheme + ".");
        }
    }

    public DatabaseSection getScheme(String scheme) {
        return schemes.get(scheme);
    }
}
