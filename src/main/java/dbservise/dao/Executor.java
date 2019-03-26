package dbservise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Executor {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
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
