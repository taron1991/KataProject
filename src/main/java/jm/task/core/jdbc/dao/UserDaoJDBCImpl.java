package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.*;


public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE = "create table \"User\"(id serial primary key,name text,lastName text,age int)";
    private static final String DROP = "DROP TABLE \"User\"";
    private static final String INSERT = "insert into \"User\"(name,lastName,age) values(?,?,?)";
    private static final String REMOVE_BY_ID = "delete from \"User\" where id = ?";
    private static final String FIND_ALL = "select*from \"User\"";
    private static final String TRUNCATE = "TRUNCATE TABLE \"User\"";
    private static Connection connection = Util.getConnection();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {


        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("что-то не так");
            try {
                connection.rollback(); // Откатываем изменения в случае ошибки
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    public void dropUsersTable() {

        try (Statement statement = connection.createStatement();) {

            statement.executeUpdate(DROP);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("что-то не так");
            try {
                connection.rollback(); // Откатываем изменения в случае ошибки
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;


        }

        System.out.println(String.format(Locale.US, "User с именем — %s добавлен в базу данных", name));
    }

    public void removeUserById(long id) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID);) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public List<User> getAllUsers() throws SQLException {

        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {

            while (resultSet.next()) {
                list.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age")));
            }

        } catch (SQLException e) {
            throw e;

        }
        return list;
    }

    public void cleanUsersTable() throws SQLException {


        try (Statement statement = connection.createStatement()) {

            statement.executeUpdate(TRUNCATE);
            connection.commit();
        } catch (SQLException e) {

            connection.rollback();
            throw e;
        }
    }
}



