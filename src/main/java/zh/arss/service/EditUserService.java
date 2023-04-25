package zh.arss.service;

import zh.arss.database.DatabaseHandler;
import zh.arss.entity.User;

public class EditUserService {
    private static final EditUserService EDIT_USER_SERVICE = new EditUserService();

    private EditUserService() {
    }

    public static EditUserService getInstance() {
        return EDIT_USER_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void stickUser(String id, String login, String password, String role) {
        if (Long.parseLong(id) == user.getIdUser()) {
            databaseHandler.updateUser(Long.parseLong(id), login, password, Boolean.valueOf(role));
        } else {
            databaseHandler.registration(login, password);
        }
    }

    public void deleteUser() {
        databaseHandler.deleteUser(user.getIdUser());
    }
}
