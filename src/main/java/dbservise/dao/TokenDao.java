package dbservise.dao;

import dbservise.dao.dbentities.Token;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenDao extends Dao<Token> {
    private static final String TOKEN_TABLE = "tokens";
    private static final String TOKEN_VALUE_COLUMN = "token";
    private static final String USER_NAME_COLUMN = "user_name";

    public TokenDao(Connection connection) {
        super(connection);
    }

    @Override
    String getInsertQuery() {
        return "INSERT INTO " + TOKEN_TABLE + "("+TOKEN_VALUE_COLUMN+","+USER_NAME_COLUMN+")"+ " VALUES (?,?);";
    }


    @Override
    String getDeleteQuery() {
        return "DELETE FROM "+TOKEN_TABLE+" WHERE "+TOKEN_VALUE_COLUMN + "=?;";
    }

    @Override
    void getInsertParamSetter(Token token, PreparedStatement statement) throws SQLException {
        statement.setString(1, token.getValue());
        statement.setString(2, token.getUserName());
    }

    @Override
    void getDeleteParamSetter(Token token, PreparedStatement statement) throws SQLException {
        statement.setString(1, token.getValue());
    }

    @Override
    Token parseResult(ResultSet resultSet) throws SQLException {
        return new Token (resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
    }

    public void insertToken (Token token) throws SQLException{
        insert(token);
    }

    public Token getTokenByValue (String value) throws SQLException{
        return getBy(TOKEN_TABLE, TOKEN_VALUE_COLUMN, value);
    }

    public void deleteToken (String value) throws SQLException{
        delete(getTokenByValue(value));
    }


}
