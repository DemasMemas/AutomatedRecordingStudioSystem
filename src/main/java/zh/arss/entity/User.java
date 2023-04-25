package zh.arss.entity;

public class User {
    private long idUser;
    private String login;
    private String password;
    private boolean isAdministrator;

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
}
