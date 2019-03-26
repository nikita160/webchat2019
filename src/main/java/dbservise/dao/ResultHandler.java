package dbservise.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultHandler<E> {
    E handle(ResultSet resultSet) throws SQLException;
}
