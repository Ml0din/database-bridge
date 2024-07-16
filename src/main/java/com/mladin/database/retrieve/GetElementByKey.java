package com.mladin.database.retrieve;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableRetrieve;
import com.mladin.database.TableActionResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetElementByKey extends TableRetrieve<Object> {
    protected Runnable errorEncountered;
    protected Runnable afterRun;

    public GetElementByKey(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, TableActionResult<Object> actionResult, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + params[0].toString() + " FROM " + databaseTable.getName() + " WHERE " + params[1].toString() + " = ?");
        preparedStatement.setString(1, params[2].toString());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        if(resultSet.getObject(1) != null) {
            actionResult.setResult(resultSet.getObject(1));
        }else {
            actionResult.setResult("N/A");
        }

        preparedStatement.close();
        resultSet.close();
        connection.close();
    }
}
