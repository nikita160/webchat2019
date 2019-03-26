package dbservise;

import dbservise.dao.AccountServiceDao;

import model.accountservice.AccountService;
import model.accountservice.AuthDataSet;
import model.accountservice.datasets.CreateUserDataSet;
import model.accountservice.datasets.MainPageDataSet;
import model.authservice.AuthService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBAccountService implements AccountService {

    private final ConnectionBuilder builder;
    private final Connection connection;
    //private final UserDao userDao;
    //private final TokenDao tokenDao;
    //private final ContactDao contactDao;
    private final AccountServiceDao dao;


    public DBAccountService(ConnectionBuilder builder) {
        this.builder = builder;
        this.connection = builder.getConnection();
        this.dao = new AccountServiceDao(connection);
        //this.userDao = new UserDao(connection);
        //this.tokenDao = new TokenDao(connection);
        //this.contactDao = new ContactDao(connection);
    }



    @Override
    public void createNewUser(CreateUserDataSet createUserDataSet) throws DBException {

        try {
            connection.setAutoCommit(false);
            dao.insertNewUser(createUserDataSet);
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }

    }

    @Override
    public int verify(AuthService authService, AuthDataSet current) throws DBException {
        try {
            AuthDataSet expected = dao.getAuthDataSet(current.getLogin());
            if (expected==null) return 0;
            else {
                if (authService.check(expected,current)) return 1;
                else return -1;
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean isUserExist(String login) throws DBException {
        try {
            return (dao.getUserBylogin(login)==null)?false:true;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    /*@Override
    public List<String> getAllLogin() throws DBException {
        try {
            return dao.getAllUserLogins();

        } catch (SQLException e) {
            throw new DBException(e);
        }
    }*/

    @Override
    public MainPageDataSet getMainPageDataSet(String login) throws DBException {
        try {
            //UserDao userDao = new UserDao(connection);
            MainPageDataSet dataSet = new MainPageDataSet(dao.getUserContactList(login));
            return dataSet;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public String getLoginByToken(String token) throws DBException{

        try {
            return dao.getUserLoginByToken(token);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean isFriend(String loginToCheck, String ownerLogin) throws DBException {
        try {
            if (dao.getSingleContact(ownerLogin, loginToCheck)==null) return false;
            else return true;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void createUserToken(String login, String token) throws DBException {
        try {
            connection.setAutoCommit(false);
            dao.insertNewToken(token, login);
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public void deleteToken(String token) throws DBException {
        try {
            connection.setAutoCommit(false);
            dao.deleteToken(token);
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    @Override
    public List<String> searchUser(String pattern, String currentLogin) throws DBException {
        try {
            return dao.getUserListByPattern(pattern, currentLogin);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    public int addContactToUserContactList(String ownerLogin, String contactLogin) throws DBException {
        try {

           try {
               dao.insertNewContact(ownerLogin, contactLogin);
               return 1;
           }
           catch (UserNotExistException e){

               return -1;
           }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }
}
