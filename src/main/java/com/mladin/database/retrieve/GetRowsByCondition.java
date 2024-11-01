package com.mladin.database.retrieve;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableRetrieve;
import com.mladin.database.TableActionResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;

public class GetRowsByCondition extends TableRetrieve<HashMap<Object, LinkedList<Object>>> {
    public GetRowsByCondition(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, TableActionResult<HashMap<Object, LinkedList<Object>>> actionResult, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();

        int conditions = Integer.parseInt(params[0].toString());

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + databaseTable.getName() + " WHERE " + params[1]);
        for(int i = 1; i <= conditions; i++) {
            preparedStatement.setObject(i, params[i + 1]);
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        HashMap<Object, LinkedList<Object>> data = new HashMap<>();


        while (resultSet.next()) {
            LinkedList<Object> dataSet = new LinkedList<>();
            for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                dataSet.add(resultSet.getObject(i + 1));
            }

            data.put(resultSet.getObject(1), dataSet);
        }

        actionResult.setResult(data);

        preparedStatement.close();
        connection.close();
    }
}
