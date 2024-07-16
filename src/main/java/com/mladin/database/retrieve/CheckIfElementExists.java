package com.mladin.database.retrieve;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableActionResult;
import com.mladin.database.TableRetrieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckIfElementExists extends TableRetrieve<Boolean> {
    public CheckIfElementExists(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, TableActionResult<Boolean> actionResult, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + databaseTable.getName() + " WHERE " + params[0].toString() + " = ?");
        preparedStatement.setString(1, params[1].toString());

        ResultSet resultSet = preparedStatement.executeQuery();
        Boolean result = resultSet.next();
        actionResult.setResult(result);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
