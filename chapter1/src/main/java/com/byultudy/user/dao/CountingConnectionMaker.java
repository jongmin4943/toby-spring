package com.byultudy.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker{
    private int counter = 0;
    private ConnectionMaker connectionMaker;

    public void setConnectionMaker(final ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        counter++;
        return connectionMaker.makeNewConnection();
    }

    public int getCounter() {
        return counter;
    }
}
