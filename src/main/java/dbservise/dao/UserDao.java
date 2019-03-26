package dbservise.dao;

import dbservise.dao.dbentities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDao extends Dao<User> {

    private static final String USER_TABLE = "users";
    //private static final String USERCONTACT_TABLE = "userscontact";
    private static final String ID_COLUMN = "user_id";
    private static final String NAME_COLUMN = "name";
    private static final String PASSWORD_COLUMN = "password";


    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    String getInsertQuery() {
        return "INSERT INTO " + USER_TABLE +"("+NAME_COLUMN+","+PASSWORD_COLUMN+")"+ " VALUES (?,?);";
    }

    @Override
    String getDeleteQuery() {
        return "DELETE FROM "+USER_TABLE+" WHERE "+NAME_COLUMN + "=(?);";
    }

    @Override
    void getInsertParamSetter(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
    }

    @Override
    void getDeleteParamSetter(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getName());
    }

    @Override
    User parseResult(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
    }

    public User getById(long id) throws SQLException {
        return getBy(USER_TABLE, ID_COLUMN, String.valueOf(id));
    }

    /*public List<UserAccountDataSet> getUserListByPattern (String pattern) throws SQLException{
        return getListBy(USER_TABLE, USERCONTACT_TABLE, "%" + pattern + "%");
    }*/

    public List<String> getNameListByPatternExcl (String pattern, String exclValue) throws SQLException{
        String query = "SELECT * FROM " + USER_TABLE + " WHERE " + NAME_COLUMN + "= ? AND " +
                NAME_COLUMN + "<>?;";
        return executeSelect(query, statement -> {
            statement.setString(1, "%" + pattern + "%");
            statement.setString(2, exclValue);
        }, resultSet -> {
            List<String> list = new ArrayList();
            while (resultSet.next()) {
                list.add(resultSet.getString(2));
            }
            return list;
        });
    }



    public User getByName(String name) throws SQLException {
        return getBy(USER_TABLE, NAME_COLUMN, name);
    }

    public void insertUser(User user) throws SQLException {
        insert(user);
    }
}
