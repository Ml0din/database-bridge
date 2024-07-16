package com.mladin.database.update;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableUpdate;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SetElementsByKey extends TableUpdate {
    public SetElementsByKey(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();

        StringBuilder stringBuilder = new StringBuilder();
        Integer columns = (Integer) params[0];
        int cursor = 1;

        for(int i = 0; i < columns; i++) {
            if(i != columns - 1) {
                stringBuilder.append(params[cursor++].toString() + " = ?, ");
            }else {
                stringBuilder.append(params[cursor++].toString() + " = ?");
            }
        }

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + databaseTable.getName() + " SET " + stringBuilder.toString() + " WHERE " + params[params.length - 2].toString() + " = ?");

        for(int i = 0; i < columns; i++) {
            preparedStatement.setString(i + 1, params[cursor++].toString());
        }

        preparedStatement.setString(columns + 1, params[params.length - 1].toString());
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
