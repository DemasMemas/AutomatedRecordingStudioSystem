package zh.arss.database;

import zh.arss.entity.Arrangement;
import zh.arss.entity.Purchase;
import zh.arss.entity.Request;
import zh.arss.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            String request = "insert into arss_user (id_user, login, password, is_administrator) values(?,?,?,?)";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, generateNewId("arss_user", "id_user"));
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setBoolean(4, false);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return "error";
        }
        return "success";
    }

    public String insertPurchase(Long id_user, Long id_arrangement) {
        try {
            String request = "insert into purchase (id_purchase ,id_user, id_arrangement) values(?,?,?)";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, generateNewId("purchase", "id_purchase"));
            preparedStatement.setLong(2, id_user);
            preparedStatement.setLong(3, id_arrangement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return "error";
        }
        return "success";
    }

    public User getUser(long id) {
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
            user.setAdministrator(resultSet.getBoolean("is_administrator"));

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertRequest(Long idUser, String date, String service, String code, String email,
                              String phone, String description) throws SQLException {
        String request = "insert into arss_request (id_request, id_user, date, service, code, email, phone, description)" +
                " values(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
        preparedStatement.setLong(1, generateNewId("arss_request", "id_request"));
        preparedStatement.setLong(2, idUser);
        preparedStatement.setString(3, date);
        preparedStatement.setString(4, service);
        preparedStatement.setString(5, code);
        preparedStatement.setString(6, email);
        preparedStatement.setString(7, phone);
        preparedStatement.setString(8, description);
        preparedStatement.executeUpdate();
    }

    public List<Request> getAllRequest() {
        List<Request> requests = new ArrayList<>();
        try {
            String request = "select * from arss_request";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Request userRequest = new Request();
                userRequest.setIdRequest(resultSet.getLong("id_request"));
                userRequest.setIdUser(resultSet.getLong("id_user"));
                userRequest.setDate(resultSet.getString("date"));
                userRequest.setService(resultSet.getString("service"));
                userRequest.setCode(resultSet.getString("code"));
                userRequest.setEmail(resultSet.getString("email"));
                userRequest.setPhone(resultSet.getString("phone"));
                userRequest.setDescription(resultSet.getString("description"));

                requests.add(userRequest);
            }

            return requests;
        }
        catch (Exception exception) {
            return requests;
        }
    }

    public void buyArrangement(long id) throws SQLException {
        String request = "update arrangement set status = 'purchased' where id_arrangement = ?";
        PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    public List<Arrangement> getAllArrangement() {
        List<Arrangement> arrangements = new ArrayList<>();
        try {
            String request = "select * from arrangement";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Arrangement arrangement = new Arrangement();
                arrangement.setIdArrangement(resultSet.getLong("id_arrangement"));
                arrangement.setName(resultSet.getString("name"));
                arrangement.setStatus(resultSet.getString("status"));

                arrangements.add(arrangement);
            }

            return arrangements;
        }
        catch (Exception exception) {
            return arrangements;
        }
    }

    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        try {
                String request = "select * from arss_user";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setIdUser(resultSet.getLong("id_user"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setAdministrator(resultSet.getBoolean("is_administrator"));

                users.add(user);
            }

            return users;
        }
        catch (Exception exception) {
            return users;
        }
    }

    public List<Purchase> getAllPurchase() {
        List<Purchase> purchases = new ArrayList<>();
        try {
            String request = "select * from purchase";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(resultSet.getLong("id_purchase"));
                purchase.setIdUser(resultSet.getLong("id_user"));
                purchase.setIdArrangement(resultSet.getLong("id_arrangement"));

                purchases.add(purchase);
            }

            return purchases;
        }
        catch (Exception exception) {
            return purchases;
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


    public void updateUser(long id, String login, String password, Boolean role) {
        try {
            String request = "update arss_user set login = ?, password = ?, role = ? where id_user = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setBoolean(3, role);
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public void updatePurchase(long id, long idUser, long idArrangement) {
        try {
            String request = "update purchase set id_user = ?, id_arrangement = ? where id_purchase = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, idUser);
            preparedStatement.setLong(2, idArrangement);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void insertArrangement(String name, String status) {
        try {
            String request = "insert into arrangement (id_arrangement, name, status) values(?,?,?)";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, generateNewId("arrangement", "id_arrangement"));
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, status);
            preparedStatement.executeUpdate();
        } catch (Exception ignored) {}
    }

    public void updateArrangement(long id, String name, String status) {
        try {
            String request = "update arrangement set name = ?, status = ? where id_arrangement = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, status);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateRequest(long id, long userId, String date, String serviceRequest,
                              String code, String email, String phone, String description) {
        try {
            String request = "update arss_request set id_user = ?, date = ?, service = ?, code = ?, email = ?," +
                    " phone = ?, description = ? where id_request = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, serviceRequest);
            preparedStatement.setString(4, code);
            preparedStatement.setString(5, email);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, description);
            preparedStatement.setLong(8, id);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteRequest(long id) {
        try {
            String request = "delete from arss_request where id_request = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteUser(long id) {
        try {
            String request = "delete from arss_user where id_user = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteArrangement(long id) {
        try {
            String request = "delete from arrangement where id_arrangement = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deletePurchase(long id) {
        try {
            String request = "delete from purchase where id_purchase = ?";
            PreparedStatement preparedStatement = CONNECTION.prepareStatement(request);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
