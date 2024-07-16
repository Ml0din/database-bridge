package com.mladin.database;

public abstract class TableUpdate {
    protected DatabaseBridge databaseBridge;
    public TableUpdate(DatabaseBridge databaseBridge) {
        this.databaseBridge = databaseBridge;
    }

    public abstract void run(DatabaseTable databaseTable, Object... params) throws Exception;
    public void runSync(DatabaseTable databaseTable, Object... params) {
        try {
            run(databaseTable, params);
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void runSync(DatabaseTable databaseTable, Runnable errorEncountered, Runnable afterRun, Object... params) {
        try {
            run(databaseTable, params);
            if(afterRun != null) {
                afterRun.run();
            }
        }catch (Exception exception) {
            exception.printStackTrace();
            if(errorEncountered != null) {
                errorEncountered.run();
            }
        }
    }

    public void runAsync(DatabaseTable databaseTable, Object... params) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runSync(databaseTable, params);
            }
        });

        thread.start();
    }

    public void runAsync(DatabaseTable databaseTable, Runnable errorEncountered, Runnable afterRun, Object... params) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runSync(databaseTable, errorEncountered, afterRun, params);
            }
        });

        thread.start();
    }
}
