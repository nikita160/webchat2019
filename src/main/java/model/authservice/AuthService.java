package model.authservice;


import model.accountservice.AuthDataSet;

public interface AuthService {

    boolean check(AuthDataSet expected, AuthDataSet current);

}
