package edu.javacourse.city.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DirectConnectionBuilder implements ConnectionBuilder {
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/city_register",
                "greem", "414510");
    }

}
