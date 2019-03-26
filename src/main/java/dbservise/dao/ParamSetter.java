package dbservise.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParamSetter {

    void setParameters (PreparedStatement statement) throws SQLException;
}
