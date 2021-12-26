package org.but.feec.javafx.data;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.javafx.api.*;
import org.but.feec.javafx.config.DataSourceConfig;
import org.but.feec.javafx.exceptions.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonRepository {

    public PersonAuthView findPersonByEmail(String last_name) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT last_name, password" +
                             " FROM public.customer p" +
                             " WHERE p.last_name = ?")
        ) {
            preparedStatement.setString(1, last_name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonAuth(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    public PersonDetailView findPersonDetailedView(Long personId) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id_person, email, first_name, surname, city, house_number, street" +
                             " FROM bds.person p" +
                             " LEFT JOIN bds.address a ON p.id_address = a.id_address" +
                             " WHERE p.id_person = ?")
        ) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find person by ID with addresses failed.", e);
        }
        return null;
    }

    /**
     * What will happen if we do not use LEFT JOIN? What persons will be returned? Ask your self and repeat JOIN from the presentations
     *
     * @return list of persons
     */
    public List<PersonBasicView> getPersonsBasicView() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT c.customer_id, email, first_name, last_name, city" +
                             " FROM public.customer c" +
                             " JOIN cust_address ca ON c.customer_id = ca.customer_id" +
                             " JOIN address a ON a.address_id=ca.address_id");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons basic view could not be loaded.", e);
        }
    }

    public void createPerson(PersonCreateView personCreateView) {
        String insertPersonSQL = "INSERT INTO public.customer (first_name, email, last_name, password, sex, registered) VALUES (?,?,?,?,?,?)";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables

            preparedStatement.setString(1, personCreateView.getFirstName());
            preparedStatement.setString(2, personCreateView.getEmail());
            preparedStatement.setString(3, personCreateView.getLastName());
            preparedStatement.setString(4, String.valueOf(personCreateView.getPwd()));
            preparedStatement.setString(5, String.valueOf(personCreateView.getGender()));
            preparedStatement.setTimestamp(6,personCreateView.getJoined());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DataAccessException("Creating person failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }

    public void editPerson(PersonEditView personEditView) {
        String insertPersonSQL = "UPDATE public.customer SET first_name = ?, last_name = ?, email = ? WHERE customer_id = ?";
        String checkIfExists = "SELECT last_name FROM public.customer WHERE customer_id = ?";
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables

            preparedStatement.setString(1, personEditView.getFirstName());
//            preparedStatement.setString(3, personEditView.getNickname());
            preparedStatement.setString(2, personEditView.getSurname());
            preparedStatement.setString(3, personEditView.getEmail());
            preparedStatement.setLong(4, personEditView.getId());

            try {
                // TODO set connection autocommit to false
                /* HERE */
                connection.setAutoCommit(false);
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, personEditView.getId());
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for edit do not exists.");
                }

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new DataAccessException("Creating person failed, no rows affected.");
                }
                // TODO commit the transaction (both queries were performed)
                /* HERE */
                connection.commit();
            } catch (SQLException e) {
                // TODO rollback the transaction if something wrong occurs
                /* HERE */
                connection.rollback();
            } finally {
                // TODO set connection autocommit back to true
                /* HERE */
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Creating person failed operation on the database failed.");
        }
    }


    /**
     * <p>
     * Note: In practice reflection or other mapping frameworks can be used (e.g., MapStruct)
     * </p>
     */
    private PersonAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        PersonAuthView person = new PersonAuthView();
        person.setEmail(rs.getString("last_name"));
        person.setPassword(rs.getString("password"));
        return person;
    }

    private PersonBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        PersonBasicView personBasicView = new PersonBasicView();
        personBasicView.setId(rs.getLong("customer_id"));
        personBasicView.setEmail(rs.getString("email"));
        personBasicView.setGivenName(rs.getString("first_name"));
        personBasicView.setFamilyName(rs.getString("last_name"));
        personBasicView.setCity(rs.getString("city"));
        return personBasicView;
    }

    private PersonDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        PersonDetailView personDetailView = new PersonDetailView();
        personDetailView.setId(rs.getLong("id_person"));
        personDetailView.setEmail(rs.getString("email"));
        personDetailView.setGivenName(rs.getString("first_name"));
        personDetailView.setFamilyName(rs.getString("last_name"));
        personDetailView.setCity(rs.getString("city"));
        personDetailView.sethouseNumber(rs.getString("house_number"));
        personDetailView.setStreet(rs.getString("street"));
        return personDetailView;
    }

}
