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
                     "SELECT c.customer_id, email, first_name, last_name, sex, city, zipcode," +
                             " street_number, street_name, country, address_type" +
                             " FROM public.customer c" +
                             " LEFT JOIN public.cust_address ca ON c.customer_id = ca.customer_id" +
                             " LEFT JOIN public.address a ON ca.address_id = a.address_id" +
                             " WHERE c.customer_id = ?")
        ) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapToPersonDetailView(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e); //"Find person by ID with addresses failed.",
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
                     "SELECT c.customer_id, email, first_name, last_name" +
                             " FROM public.customer c");
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

    public List<PersonBasicView> getPersonsBasicViewCustomer() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT c.customer_id, email, first_name, last_name" +
                             " FROM inject.customer c");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons inject view customer_1 could not be loaded.", e);
        }
    }

    public List<PersonBasicView> getPersonsBasicViewCustomer_1() {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT c.customer_id, email, first_name, last_name" +
                             " FROM inject.customer_1 c");
             ResultSet resultSet = preparedStatement.executeQuery();) {
            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Persons inject view customer_1 could not be loaded.", e);
        }
    }

    public void createPerson(PersonCreateView personCreateView) {
        String insertPersonSQL = "INSERT INTO public.customer (first_name, email, last_name, " +
                "password, sex, registered) VALUES (?,?,?,?,?,?)";
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
            throw new DataAccessException("Creating person failed operation on the database .");
        }
    }


    public void deletePerson(PersonDeleteView personDeleteView) {
        String deletePersonSQL = "DELETE FROM public.customer WHERE customer_id = ?";
        String checkIfExists = "SELECT last_name FROM public.customer WHERE customer_id = ?";
        Long id = personDeleteView.getPersonId();
        try (Connection connection = DataSourceConfig.getConnection();
             // would be beneficial if I will return the created entity back
             PreparedStatement preparedStatement = connection.prepareStatement(deletePersonSQL, Statement.RETURN_GENERATED_KEYS)) {
            // set prepared statement variables
            System.out.println("delete id "+id);

            preparedStatement.setLong(1, id);

            try {
                // TODO set connection autocommit to false
                /* HERE */
                connection.setAutoCommit(false);
                try (PreparedStatement ps = connection.prepareStatement(checkIfExists, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, id);
                    ps.execute();
                } catch (SQLException e) {
                    throw new DataAccessException("This person for delete do not exists.");
                }


                int affectedRows = preparedStatement.executeUpdate();
                System.out.println(affectedRows);


                if (affectedRows == 0) {
                    throw new DataAccessException("Deleting person failed, no rows affected.");
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
            throw new DataAccessException("Deleting person failed operation on the database .");
        }
    }

    public List<PersonBasicView> getPersonsFilterView(String lastName) {
        try (Connection connection = DataSourceConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT customer_id, email, first_name, last_name" +
                             " FROM public.customer WHERE last_name = ?");) {
            preparedStatement.setString(1, lastName);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException( e); //"Persons filtered view could not be loaded."
        }
    }

//    todo dorobit filter view na obe tabulky, pokusist sa o injection, asi prerobit na concatenovanie
    public List<PersonBasicView> getPersonsInjectionView1(String lastName) {
        try (Connection connection = DataSourceConfig.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT customer_id, email, first_name, last_name" +
                    " FROM inject.customer WHERE last_name = '" + lastName + "'";

            ResultSet resultSet = statement.executeQuery(query);

            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Table customer was not injected",e); //"Persons filtered view could not be loaded."
        }
    }

    public List<PersonBasicView> getPersonsInjectionView2(String lastName) {
        try (Connection connection = DataSourceConfig.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT customer_id, email, first_name, last_name" +
                    " FROM inject.customer_1 WHERE last_name = '" + lastName + "'";

            ResultSet resultSet = statement.executeQuery(query);

            List<PersonBasicView> personBasicViews = new ArrayList<>();
            while (resultSet.next()) {
                personBasicViews.add(mapToPersonBasicView(resultSet));
            }
            return personBasicViews;
        } catch (SQLException e) {
            throw new DataAccessException("Table customer_1 was not injected",e); //"Persons filtered view could not be loaded."
        }
    }


    /**
     * <p>
     * Note: In practice reflection or other mapping frameworks can be used (e.g., MapStruct)
     * </p>
     */
    private PersonAuthView mapToPersonAuth(ResultSet rs) throws SQLException {
        PersonAuthView person = new PersonAuthView();
        person.setLastName(rs.getString("last_name"));
        person.setPassword(rs.getString("password"));
        return person;
    }

    private PersonBasicView mapToPersonBasicView(ResultSet rs) throws SQLException {
        PersonBasicView personBasicView = new PersonBasicView();
        personBasicView.setId(rs.getLong("customer_id"));
        personBasicView.setEmail(rs.getString("email"));
        personBasicView.setGivenName(rs.getString("first_name"));
        personBasicView.setFamilyName(rs.getString("last_name"));
//        personBasicView.setCity(rs.getString("city"));
        return personBasicView;
    }

    private PersonDetailView mapToPersonDetailView(ResultSet rs) throws SQLException {
        PersonDetailView personDetailView = new PersonDetailView();
        personDetailView.setId(rs.getLong("customer_id"));
        personDetailView.setEmail(rs.getString("email"));
        personDetailView.setGivenName(rs.getString("first_name"));
        personDetailView.setFamilyName(rs.getString("last_name"));
        personDetailView.setGender(rs.getString("sex"));
        personDetailView.sethouseNumber(rs.getString("street_number"));
        personDetailView.setStreet(rs.getString("street_name"));
        personDetailView.setCity(rs.getString("city"));
        personDetailView.setZipcode(rs.getString("zipcode"));
        personDetailView.setCountry(rs.getString("country"));
        personDetailView.setAddressType(rs.getString("address_type"));

        return personDetailView;
    }



}
