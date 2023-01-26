package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.utilities.PasswordHasher;

import java.sql.SQLException;

public class MainService {
    private static final MainService MAIN_SERVICE = new MainService();

    private MainService() {
    }

    public static MainService getInstance() {
        return MAIN_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

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

            //TODO nextService.setUser(databaseHandler.getUser(Long.parseLong(response)));

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
}
