package zh.arss.database;

import zh.arss.entity.User;

import java.sql.*;

public class DatabaseHandler {
    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    public static DatabaseHandler getInstance() {
        return databaseHandler;
    }

    private Connection CONNECTION;

    private DatabaseHandler() {
        String connectionString;
        try {
            connectionString = "jdbc:postgresql://localhost:5432/arss";
            Class.forName("org.postgresql.Driver");
            CONNECTION = DriverManager.getConnection(connectionString, "postgres", "890123890123");
        } catch (ClassNotFoundException | SQLException e) {
            connectionString = "jdbc:mysql://localhost:3306/arss";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                CONNECTION = DriverManager.getConnection(connectionString, "root", "root");
            } catch (ClassNotFoundException | SQLException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    public String authorization(String login, String password) throws SQLException {
        String request = "select * from arss_user where login = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return "login not found";
        }
        String request2 = "select * from arss_user where login = ? and password = ?";
        preparedStatement = CONNECTION.prepareStatement(request2);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();

        return resultSet.next() ? String.valueOf(resultSet.getInt("id_user")) : "incorrect password";
    }

    public String registration(String login, String password) {
        try {
            String request = "insert into arss_user (id_user, login, password) values(?,?,?)";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, generateNewId("arss_user", "id_user"));
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return "error";
        }
        return "success";
    }

    public User getUser(long id){
        User user = new User();
        try {
            String request = "select * from arss_user where id_user = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            user.setIdUser(resultSet.getLong("id_user"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateNewId(String table, String idName) throws SQLException {
        String request = "select " + idName + " from " + table;
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
        ResultSet resultSet = preparedStatement.executeQuery();
        long newId = 0;
        try {
            while (resultSet.next()) {
                long thisId = Long.parseLong(resultSet.getString(idName));
                if (newId < thisId) {
                    newId = thisId;
                }
            }
        } catch (Exception ignored) {
        }
        return ++newId;
    }
}
