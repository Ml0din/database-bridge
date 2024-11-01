package com.mladin.database;

public class TableActionResult<ResultType> {
    protected ResultType result;
    protected boolean encounteredError = false;

    public void setResult(ResultType result) {
        this.result = result;
    }

    public boolean hasEncounteredError() {
        return this.encounteredError;
    }

    public void setEncounteredError(boolean value) {
        this.encounteredError = value;
    }

    public ResultType getResult() {
        return this.result;
    }
}
