package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.entity.Request;
import zh.arss.entity.User;
import zh.arss.utilities.PasswordHasher;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class MainService {
    private static final MainService MAIN_SERVICE = new MainService();

    private MainService() {
    }

    public static MainService getInstance() {
        return MAIN_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private User user;

    public String authorization(String login, String password) {
        try {
            if (login.equals(""))
                return "Введите логин";
            if (password.equals(""))
                return "Введите пароль";

            password = PasswordHasher.hashingPassword(password);
            String response = databaseHandler.authorization(login, password);
            if (response.equals("login not found")) {
                return "Пользователь не найден";
            }
            if (response.equals("incorrect password")) {
                return "Неверный пароль";
            }

            user = databaseHandler.getUser(Long.parseLong(response));

            return "success";
        } catch (SQLException e) {
            return "Неизвестная ошибка";
        }
    }

    public String registration(String login, String password) {
        if (login.equals(""))
            return "Введите логин";
        if (login.length() < 5)
            return "Логин короче 5 символов";
        if (password.equals(""))
            return "Введите пароль";
        if (password.length() < 5)
            return "Пароль короче 5 символов";

        password = PasswordHasher.hashingPassword(password);

        String response = databaseHandler.registration(login, password);
        if (response.equals("error")) {
            return "Ошибка регистрации";
        }
        return response;
    }

    public String createRequest(String date, String email, String phone, String description) {
        if (email.equals("")) {
            return "Ошибка";
        }
        if (phone.equals("")) {
            return "Ошибка";
        }

        date = date.split(": ")[1];
        String service;
        if (date.split(" ")[1].equals("ночь")) {
            service = "Аренда";
        } else {
            service = "Запись";
        }
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(new Random().nextInt(9));
        }

        try {
            databaseHandler.insertRequest(user.getIdUser(), date, service, code.toString(), email, phone, description);
            return code.toString();
        } catch (Exception exception) {
            return "Ошибка";
        }
    }

    public User getUser() {
        return user;
    }

    public List<Request> getAllRequest() {
        return databaseHandler.getAllRequest();
    }
}
