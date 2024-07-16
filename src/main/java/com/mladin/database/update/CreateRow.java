package com.mladin.database.update;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableUpdate;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateRow extends TableUpdate {
    public CreateRow(DatabaseBridge databaseBridge) {
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
                stringBuilder.append(params[cursor++].toString() + ", ");
            }else {
                stringBuilder.append(params[cursor++].toString());
            }
        }


        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + databaseTable.getName() + " (" + stringBuilder + ") VALUES (" + StringUtils.repeat("?, ", columns - 1) + "?)");

        for(int i = 0; i < columns; i++) {
            preparedStatement.setObject(i + 1, params[cursor++]);
        }


        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
