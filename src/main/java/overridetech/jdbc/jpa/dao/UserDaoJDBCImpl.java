package overridetech.jdbc.jpa.dao;

import overridetech.jdbc.jpa.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl(Connection connection) {
        this.connection = connection;
    }

    private final Connection connection;

    private final static String TABLE_NAME = "users";

    private final static String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
            "    id SERIAL PRIMARY KEY,\n" +
            "    name VARCHAR(255) NOT NULL,\n" +
            "    lastName VARCHAR(255) NOT NULL,\n" +
            "    age SMALLINT CHECK (age >= 0)\n" +
            ");";


    private final static String DELETE_TABLE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    private final static String INSERT_TABLE_SQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";

    private final static String DELETE_FROM_TABLE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE Id = ?";
    private final static String ALL_STR_FROM_TABLE_SQL = "SELECT * FROM " + TABLE_NAME;
    private final static String DELETE_ALL_STR_FROM_TABLE_SQL = "DELETE FROM " + TABLE_NAME;

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(CREATE_TABLE_SQL);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Не удалось создать таблицу Users");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DELETE_TABLE_SQL);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Не удалось удалить таблицу Users");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TABLE_SQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Не удалось выполнить сохранение в таблицу Users");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_FROM_TABLE_SQL)) {
            statement.setInt(1, (int) id);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Не удалось выполнить удаление пользователя из таблицы Users");
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(ALL_STR_FROM_TABLE_SQL);
            connection.commit();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                users.add(new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getByte(4)));
            }
            return users;

        } catch (SQLException e) {
            System.err.println("Не удалось получить список пользователей из таблицы Users");
        }
        return new ArrayList<>();
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_ALL_STR_FROM_TABLE_SQL);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Не удалось очистить поля таблицы Users");
        }
    }
}
