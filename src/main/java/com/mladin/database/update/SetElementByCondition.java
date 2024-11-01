package com.mladin.database.update;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableUpdate;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SetElementByCondition extends TableUpdate {
    public SetElementByCondition(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + databaseTable.getName() + " SET " + params[1] + " = ? WHERE " + params[2]);

        int placeholders = Integer.parseInt(params[0].toString());
        for(int i = 0; i < placeholders; i++) {
            preparedStatement.setObject(i + 1, params[3 + i]);
        }

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
