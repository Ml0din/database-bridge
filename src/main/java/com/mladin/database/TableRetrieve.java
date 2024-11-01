package com.mladin.database;

public abstract class TableRetrieve<ResultType> {
    protected DatabaseBridge databaseBridge;
    public TableRetrieve(DatabaseBridge databaseBridge) {
        this.databaseBridge = databaseBridge;
    }

    public abstract void run(DatabaseTable databaseTable, TableActionResult<ResultType> actionResult, Object...params) throws Exception;
    public TableActionResult<ResultType> runSync(DatabaseTable databaseTable, Object... params) {
        TableActionResult<ResultType> actionResult = new TableActionResult<>();
        try {
            if (databaseTable != null) {
                run(databaseTable, actionResult, params);
            } else {
                throw new RuntimeException("Table isn't set.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            actionResult.setEncounteredError(true);
        }

        return actionResult;
    }

    public TableActionResult<ResultType> runSync(DatabaseTable databaseTable, Runnable errorEncountered, Runnable afterFinish, Object... params) {
        TableActionResult<ResultType> actionResult = new TableActionResult<>();
        try {
            if (databaseTable != null) {
                run(databaseTable, actionResult, params);
                if (afterFinish != null) {
                    afterFinish.run();
                }
            } else {
                throw new RuntimeException("Table isn't set.");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            if (errorEncountered != null) {
                errorEncountered.run();
            }
            actionResult.setEncounteredError(true);
        }

        return actionResult;
    }
}
