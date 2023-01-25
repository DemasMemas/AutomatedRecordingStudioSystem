package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.utilities.PasswordHasher;

import java.sql.SQLException;

public class AuthorizationService {
    private static final AuthorizationService authorizationService = new AuthorizationService();

    private AuthorizationService() {
    }

    public static AuthorizationService getInstance() {
        return authorizationService;
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
}
