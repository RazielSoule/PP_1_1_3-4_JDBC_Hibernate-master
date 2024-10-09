package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users" +
                "(id SERIAL PRIMARY KEY," +
                "name CHARACTER VARYING(30)," +
                "lastName CHARACTER VARYING(30)," +
                "age INTEGER)";
        connectAndExecute(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        connectAndExecute(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (name, lastName, age)" +
                "VALUES('" + name + "', '" + lastName + "', " + age + ")";
        connectAndExecute(sql);
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE id = " + id;
        connectAndExecute(sql);
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM Users";
        connectAndExecute(sql);
    }

    public List<User> getAllUsers() {
        String sql = "SELECT id, name, lastName, age FROM Users";
        List<User> users = new ArrayList<>();
        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                users.add(user);
            }

            return users;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectAndExecute(String sql) {
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
