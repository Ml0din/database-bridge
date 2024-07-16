package com.mladin.database.retrieve;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableActionResult;
import com.mladin.database.TableRetrieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class GetElementsByKey extends TableRetrieve<LinkedList<Object>> {
    public GetElementsByKey(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, TableActionResult<LinkedList<Object>> actionResult, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();

        Integer numberOfArguments = (Integer) params[0];

        int cursor = 1;

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < numberOfArguments; i++) {
            if(i != numberOfArguments - 1) {
                stringBuilder.append(params[cursor++] + ",");
            }else {
                stringBuilder.append(params[cursor++]);
            }
        }

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + stringBuilder.toString() + " FROM " + databaseTable.getName() + " WHERE " + params[cursor++].toString() + " = ?");
        preparedStatement.setString(1, params[cursor].toString());

        ResultSet resultSet = preparedStatement.executeQuery();

        LinkedList<Object> result = new LinkedList<>();
        resultSet.next();

        for(int i = 0; i < numberOfArguments; i++) {
            result.add(resultSet.getObject(i + 1));
        }

        actionResult.setResult(result);

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
