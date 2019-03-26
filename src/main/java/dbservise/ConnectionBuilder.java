package dbservise;

import java.sql.Connection;

public interface ConnectionBuilder{
    Connection getConnection();
    void printConnectInfo();
}
