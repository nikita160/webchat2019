package model.accountservice.datasets;

public class CreateUserDataSet {

    private final String login;
    private final String password;

    public CreateUserDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
