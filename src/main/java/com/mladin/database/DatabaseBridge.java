package com.mladin.database;

import com.mladin.database.retrieve.*;
import com.mladin.database.update.*;
import org.slf4j.Logger;

import java.util.HashMap;

public class DatabaseBridge {
    protected Logger logger;

    protected String host;
    protected int port;
    protected String username;
    protected String password;

    protected HashMap<TableUpdateAction, TableUpdate> updateActions = new HashMap<>();

    public CheckIfElementExists CHECK_IF_ELEMENT_EXISTS;
    public CheckIfTableExists CHECK_IF_TABLE_EXISTS;
    public GetElementByKey GET_ELEMENT_BY_KEY;
    public GetElementsByKey GET_ELEMENTS_BY_KEY;
    public GetRowByKey GET_ROW_BY_KEY;
    public GetRowsByCondition GET_ROWS_BY_CONDITION;

    public DatabaseBridge(String host, int port, String username, String password, Logger logger) {
        this.logger = logger;

        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        init();

        logger.info("Bridge created!");
    }

    private void init() {
        this.CHECK_IF_ELEMENT_EXISTS = new CheckIfElementExists(this);
        this.CHECK_IF_TABLE_EXISTS = new CheckIfTableExists(this);
        this.GET_ELEMENT_BY_KEY = new GetElementByKey(this);
        this.GET_ELEMENTS_BY_KEY = new GetElementsByKey(this);
        this.GET_ROW_BY_KEY = new GetRowByKey(this);
        this.GET_ROWS_BY_CONDITION = new GetRowsByCondition(this);

        updateActions.put(TableUpdateAction.CREATE_ROW, new CreateRow(this));
        updateActions.put(TableUpdateAction.DELETE_ROW_BY_KEY, new DeleteRowByKey(this));
        updateActions.put(TableUpdateAction.SET_ELEMENT_BY_KEY, new SetElementByKey(this));
        updateActions.put(TableUpdateAction.SET_ELEMENTS_BY_KEY, new SetElementsByKey(this));
        updateActions.put(TableUpdateAction.SET_ELEMENT_BY_CONDITION, new SetElementByCondition(this));
    }
    public TableUpdate callUpdate(TableUpdateAction tableUpdateAction) {
        return updateActions.get(tableUpdateAction);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
