package model.accountservice;


import model.accountservice.datasets.CreateUserDataSet;
import model.accountservice.datasets.MainPageDataSet;
import model.authservice.AuthService;

import java.util.List;

public interface AccountService {


    void createNewUser(CreateUserDataSet createUserDataSet) throws Exception;

    //List<String> getAllLogin () throws Exception;

    boolean isUserExist (String login) throws Exception;

    //>0 - OK, =0 - not exist, <0 - wrong password
    int verify(AuthService authService, AuthDataSet current) throws Exception;

    MainPageDataSet getMainPageDataSet (String login) throws Exception;

    String getLoginByToken (String token) throws Exception;

    boolean isFriend (String loginToCheck, String ownerLogin) throws Exception;

    void createUserToken(String login, String token) throws Exception;

    void deleteToken(String token) throws Exception;

    List<String> searchUser(String pattern, String currentLogin) throws Exception;

    int addContactToUserContactList(String ownerLogin, String contactLogin) throws Exception;

    //AuthDataSet getAuthDataSetByLogin (String login);

    //UserAccountDataSet getUserByLogin (String login) throws Exception;

    //boolean getAnonymousPolicy();

    //String getUserByToken(String token) throws Exception;

    //void setUserToken(String login, String token) throws Exception;
















}
