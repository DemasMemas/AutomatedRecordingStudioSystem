package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.utilities.PasswordHasher;

public class RegistrationService {
    private static final RegistrationService registrationService = new RegistrationService();

    private RegistrationService() {
    }

    public static RegistrationService getInstance() {
        return registrationService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    public String registration(String name, String login, String password) {
        if (name.equals(""))
            return "Введите имя";
        if (login.equals(""))
            return "Введите логин";
        if (login.length() < 5)
            return "Логин короче 5 символов";
        if (password.equals(""))
            return "Введите пароль";
        if (password.length() < 5)
            return "Пароль короче 5 символов";

        password = PasswordHasher.hashingPassword(password);

        return databaseHandler.registration(name, login, password);
    }
}
