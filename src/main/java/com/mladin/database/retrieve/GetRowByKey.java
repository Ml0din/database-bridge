package com.mladin.database.retrieve;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableRetrieve;
import com.mladin.database.TableActionResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class GetRowByKey extends TableRetrieve<LinkedList<Object>> {
    public GetRowByKey(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, TableActionResult<LinkedList<Object>> actionResult, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + databaseTable.getName() + " WHERE " + params[0].toString() + " = ?");
        preparedStatement.setString(1, params[1].toString());

        ResultSet resultSet = preparedStatement.executeQuery();

        LinkedList<Object> result = new LinkedList<>();
        resultSet.next();

        for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            result.add(resultSet.getObject(i + 1));
        }

        actionResult.setResult(result);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
