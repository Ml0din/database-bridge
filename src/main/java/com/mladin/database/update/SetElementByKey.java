package com.mladin.database.update;

import com.mladin.database.DatabaseBridge;
import com.mladin.database.DatabaseTable;
import com.mladin.database.TableUpdate;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SetElementByKey extends TableUpdate {
    public SetElementByKey(DatabaseBridge databaseBridge) {
        super(databaseBridge);
    }

    @Override
    public void run(DatabaseTable databaseTable, Object... params) throws Exception {
        Connection connection = databaseTable.getConnectionPool().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + databaseTable.getName() + " SET " + params[0].toString() + " = ? WHERE "  + params[2].toString() + " = ?");
        preparedStatement.setString(1, params[1].toString());
        preparedStatement.setString(2, params[3].toString());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
