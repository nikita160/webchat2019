package dbservise.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public abstract class Dao<T> {

    private Connection connection;
    //private static final String SELECT_QUERY = "SELECT * FROM ? WHERE ?=?;";

    public Dao(Connection connection) {
        this.connection = connection;
    }


    abstract T parseResult(ResultSet resultSet) throws SQLException;

    abstract String getInsertQuery();
    abstract String getDeleteQuery();


    abstract void getInsertParamSetter(T t, PreparedStatement statement) throws SQLException;
    abstract void getDeleteParamSetter(T t, PreparedStatement statement) throws SQLException;

    void insert(T t) throws SQLException {
        executeUpdate(getInsertQuery(), statement -> getInsertParamSetter(t, statement));
    }

    void delete (T t) throws SQLException{
        executeUpdate(getDeleteQuery(), statement -> getDeleteParamSetter(t, statement));
    }

    String getSelectQuery (String table, String column){
        return "SELECT * FROM "+table+" WHERE "+column+ " = ?;";
    }


    T getBy(String table, String column, String value) throws SQLException {

        return executeSelect(getSelectQuery(table, column),
                statement -> {
                    statement.setString(1, value);
                },
                resultSet -> {
                    resultSet.next();
                    return parseResult(resultSet);
                });
    }

    List<T> getListBy(String table, String column, String value) throws SQLException {
        return executeSelect(getSelectQuery(table, column),
                statement -> {
                    statement.setString(1, value);
                },
                resultSet -> {
                    List<T> list = new ArrayList();
                    while (resultSet.next()) {
                        list.add(parseResult(resultSet));
                    }
                    return list;
                });
    }


    void executeUpdate(String query, ParamSetter setter) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        System.out.println(query);
        setter.setParameters(statement);
        statement.executeUpdate();
        statement.close();
    }


    <E> E executeSelect(String query, ParamSetter setter, ResultHandler<E> resultParser) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        setter.setParameters(statement);
        System.out.println(query);
        ResultSet rs = statement.executeQuery();
        E value = resultParser.handle(rs);
        rs.close();
        statement.close();
        return value;
    }
}
