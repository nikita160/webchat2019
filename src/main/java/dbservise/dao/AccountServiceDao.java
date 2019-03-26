package dbservise.dao;

import dbservise.DBException;
import dbservise.UserNotExistException;
import dbservise.dao.dbentities.Contact;
import dbservise.dao.dbentities.User;
import model.accountservice.AuthDataSet;
import model.accountservice.datasets.CreateUserDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AccountServiceDao {

    private final Executor executor;
    private final Connection connection;

    private static final String USER_TABLE = "users";

    private static final String ID_COLUMN = "user_id";
    private static final String NAME_COLUMN = "name";
    private static final String PASSWORD_COLUMN = "password";


    private final static String CONTACT_TABLE = "contacts";
    private final static String OWNER_COLUMN = "owner";
    private final static String CONTACT_COLUMN = "contact";


    private static final String TOKEN_TABLE = "tokens";
    private static final String TOKEN_VALUE_COLUMN = "token";
    private static final String TOKEN_OWNER = "user_name";


    public AccountServiceDao(Connection connection) {
        this.connection = connection;
        this.executor = new Executor(connection);
    }

    public User getUserBylogin(String login) throws SQLException {
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + NAME_COLUMN + "=?;";
        return executor.executeSelect(query, statement -> statement.setString(1, login), resultSet -> {
            if (resultSet.next())
            return new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
            else return null;
        });
    }


    public AuthDataSet getAuthDataSet(String login) throws SQLException {
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + NAME_COLUMN + "=?;";
        return executor.executeSelect(query, statement -> statement.setString(1, login), resultSet -> {
            resultSet.next();
            return new AuthDataSet(resultSet.getString(2), resultSet.getString(3));
        });
    }

    public void insertNewUser(CreateUserDataSet dataSet) throws SQLException {
        String query = "INSERT INTO " + USER_TABLE + "(" + NAME_COLUMN + "," + PASSWORD_COLUMN + ")" + " VALUES (?,?);";
        executor.executeUpdate(query, statement -> {
            statement.setString(1, dataSet.getLogin());
            statement.setString(2, dataSet.getPassword());
        });
    }


    public List<String> getAllUserLogins() throws SQLException {
        String query = "SELECT " + NAME_COLUMN + " FROM " + USER_TABLE + ";";
        return executor.executeSelect(query, null, resultSet -> {
            List<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        });
    }



    public List<String> getUserContactList(String login) throws SQLException {
        String query = "SELECT " + NAME_COLUMN + " FROM " + USER_TABLE + " WHERE " + ID_COLUMN + "=" +
                "ANY (SELECT " + CONTACT_COLUMN + " FROM " + CONTACT_TABLE + " WHERE " + OWNER_COLUMN +
                "=(SELECT " + ID_COLUMN + " FROM " + USER_TABLE + " WHERE " + NAME_COLUMN + "=?));";
        return executor.executeSelect(query, statement -> statement.setString(1, login), resultSet -> {
            List<String> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        });
    }

    public String getUserLoginByToken(String token) throws SQLException {
        String query = "SELECT * FROM " + TOKEN_TABLE + " WHERE " + TOKEN_VALUE_COLUMN + "=?;";
        return executor.executeSelect(query, statement -> statement.setString(1, token), resultSet -> {
            resultSet.next();
            return resultSet.getString(3);
        });
    }


    public Contact getSingleContact(String ownerLogin, String contactLogin) throws SQLException {
        StringBuffer query = new StringBuffer();
        query.
                append("SELECT * FROM ").
                append(CONTACT_TABLE).
                append(" WHERE ").
                append(OWNER_COLUMN).
                append("= (SELECT ").
                append(ID_COLUMN).
                append(" FROM ").
                append(USER_TABLE).
                append(" WHERE ").
                append(NAME_COLUMN).append(" = ?) AND ").
                append(CONTACT_COLUMN).
                append("=(SELECT ").
                append(ID_COLUMN).
                append(" FROM ").
                append(USER_TABLE).
                append(" WHERE ").
                append(NAME_COLUMN).
                append(" = ?);");

        return executor.executeSelect(query.toString(), statement -> {
                    statement.setString(1, ownerLogin);
                    statement.setString(2, contactLogin);
                },
                resultSet -> {
                    if (resultSet.next())
                    return new Contact(resultSet.getLong(1), resultSet.getLong(2), resultSet.getLong(3));
                    else return null;
                });

    }

    public void insertNewToken(String token, String login) throws SQLException {
        String query = "INSERT INTO " + TOKEN_TABLE + "(" + TOKEN_VALUE_COLUMN + "," + TOKEN_OWNER + ")" + " VALUES (?,?);";
        executor.executeUpdate(query, statement -> {
            statement.setString(1, token);
            statement.setString(2, login);
        });
    }

    public void deleteToken(String token) throws SQLException {
        String query = "DELETE FROM  " + TOKEN_TABLE + " WHERE " + TOKEN_VALUE_COLUMN + "=?;";
        executor.executeUpdate(query, statement -> {
            statement.setString(1, token);
        });
    }

    public List<String> getUserListByPattern(String pattern, String exl) throws SQLException {
        String patternUpd = "%"+pattern+"%";
        User user = getUserBylogin(exl);
        long id = user.getId();
        String query = "SELECT " + NAME_COLUMN + " FROM  " + USER_TABLE + " WHERE " + NAME_COLUMN + " LIKE ? AND "
                + NAME_COLUMN + "<>? AND "+ ID_COLUMN+"<>"+"ALL (SELECT "+CONTACT_COLUMN+" FROM "+ CONTACT_TABLE
                + " WHERE "+OWNER_COLUMN+"=(SELECT " + ID_COLUMN + " FROM " + USER_TABLE + " WHERE "+ NAME_COLUMN +"=?));";
        return executor.executeSelect(query, statement -> {
            statement.setString(1, patternUpd);
            statement.setString(2, exl);
            statement.setString(3,exl);
        }, resultSet -> {
            List<String> list = new ArrayList();
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
            return list;
        });
    }

    public void insertNewContact (String ownerLogin, String contactLogin) throws SQLException, UserNotExistException {
        User owner = getUserBylogin(ownerLogin);
        User contact = getUserBylogin(contactLogin);
        if (owner==null||contact==null) throw new UserNotExistException("user don't exist");
        String query = "INSERT INTO "+CONTACT_TABLE +" ("+OWNER_COLUMN+","+CONTACT_COLUMN+") VALUES (?,?);";
        executor.executeUpdate(query, statement -> {
            statement.setLong(1, owner.getId());
            statement.setLong(2, contact.getId());
        });

    }


}
