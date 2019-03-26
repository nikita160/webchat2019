package model.authservice;


import model.accountservice.AuthDataSet;

public class SimpleAuthService implements AuthService {

    @Override
    public boolean check(AuthDataSet expected, AuthDataSet current) {
        if (expected.getLogin().equals(current.getLogin()) && expected.getPassword().equals(current.getPassword()))
            return true;
        else return false;
    }
}
