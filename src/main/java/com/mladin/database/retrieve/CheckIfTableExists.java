package com.mladin.database.retrieve;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableActionResult;
import com.mladin.database.TableRetrieve;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class CheckIfTableExists extends TableRetrieve<Boolean> {

    public CheckIfTableExists(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, TableActionResult<Boolean> actionResult, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, params[0].toString(), new String[] {"TABLE"});
        actionResult.setResult(resultSet.next() && resultSet.getString("TABLE_NAME").equalsIgnoreCase(params[0].toString()));

        resultSet.close();
        connection.close();
    }
}
