package edu.javacourse.city.dao;

import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class PersonCheckDao {
    private static final String SQL_REQUEST =
            "select temporal from cr_address_person ap " +
                    "         inner join cr_person p on p.person_id = ap.person_id " +
                    "         inner join cr_address a on a.address_id = ap.address_id " +
                    "where upper(p.sur_name COLLATE \"ru_RU.utf8\") = upper(? COLLATE \"ru_RU.utf8\") " +
                    "  and upper(p.given_name) = upper(? COLLATE \"ru_RU.utf8\")" +
                    "  and upper(patronymic COLLATE \"ru_RU.utf8\") = upper(? COLLATE \"ru_RU.utf8\") " +
                    "  and p.date_of_birth = ?" +
                    "  and a.street_code = ?" +
                    "  and upper(a.building COLLATE \"ru_RU.utf8\") = upper(? COLLATE \"ru_RU.utf8\") " +
                    "  and upper(extension COLLATE \"ru_RU.utf8\") = upper(? COLLATE \"ru_RU.utf8\") " +
                    "  and upper(a.apartment COLLATE \"ru_RU.utf8\") = upper(? COLLATE \"ru_RU.utf8\") " ;

//    private static final String SQL_REQUEST =
//            "select temporal from cr_address_person ap " +
//                    "inner join cr_person p on p.person_id = ap.person_id " +
//                    "inner join cr_address a on a.address_id = ap.address_id " +
//                    "where " +
//                    "CURRENT_DATE >= ap.start_date and (CURRENT_DATE <= ap.end_data or ap.end_data is null)" +
//                    "and upper(p.sur_name COLLATE \"en_US.UTF-8\") = upper(? COLLATE \"en_US.UTF-8\")  " +
//                    "and upper(p.given_name COLLATE \"en_US.UTF-8\") = upper(? COLLATE \"en_US.UTF-8\")  " +
//                    "and upper(patronymic COLLATE \"en_US.UTF-8\") = upper(? COLLATE \"en_US.UTF-8\")  " +
//                    "and p.date_of_birth = ? " +
//                    "and a.street_code = ?  " +
//                    "and upper(a.building COLLATE \"en_US.UTF-8\") = upper(? COLLATE \"en_US.UTF-8\")  ";


    public PersonResponse checkPerson(PersonRequest request) throws PersonCheckException {
        PersonResponse response = new PersonResponse();


        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_REQUEST)) {

            stmt.setString(1, request.getSurName());
            stmt.setString(2, request.getGivenName());
            stmt.setString(3, request.getPatronymic());
            stmt.setDate(4, java.sql.Date.valueOf(request.getDateOfBirth()));
            stmt.setInt(5, request.getStreetCode());
            stmt.setString(6, request.getBuilding());
            stmt.setString(7, request.getExtension());
            stmt.setString(8,request.getApartment());

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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres",
                "greem", "414510");
    }
}
