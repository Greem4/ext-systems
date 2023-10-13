package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class PersonCheckDao {
    private static final String SQL_REQUEST = "";

    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();


        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_REQUEST)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                response.setRegistered(true);
                response.setTemporal(rs.getBoolean("temporal"));
            }

        } catch (SQLException ex) {
            throw new PersonCheckException(ex);
        }

        return response;
    }

    private Connection getConnection() {

        return null;
    }
}
