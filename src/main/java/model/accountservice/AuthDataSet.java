package model.accountservice;


public class AuthDataSet {

    private final String login;
    private final String password;


    public AuthDataSet(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
