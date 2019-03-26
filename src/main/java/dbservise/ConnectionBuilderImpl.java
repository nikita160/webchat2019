package dbservise;

import main.ServerBuilder;
import main.config.ServerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionBuilderImpl implements ConnectionBuilder {

    private final Connection connection;
    private final ServerConfig config;
    private static final Logger logger = LogManager.getLogger(ConnectionBuilder.class);

    public ConnectionBuilderImpl(ServerConfig config){
        this.config = config;
        String dbDriverName = config.getDbDriver().trim().toLowerCase();
        switch (dbDriverName){
            case "mysql":this.connection=getMySQLConnection();break;
            default: this.connection=getH2Connection();break;
        }

        printConnectInfo();
    }

    public Connection getConnection(){
        return connection;
    }

    public void printConnectInfo() {
        try {
            logger.info("DB name: " + connection.getMetaData().getDatabaseProductName());
            logger.info("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            logger.info("Driver: " + connection.getMetaData().getDriverName());
            logger.info("Autocommit: " + connection.getAutoCommit());
            logger.info("*******Connection succesfully created******");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getMySQLConnection(){
        try{
            logger.info("Try to get MySQL Connection");
            String driverClass = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:"+config.getDbPort()+"/"+config.getDbName()+"?useJDBCCompliantTimezoneShift=true&serverTimezone=UTC";
            System.out.println(url);
            Class.forName(driverClass);
            Connection connection = DriverManager.getConnection(url, config.getDbLogin(), config.getDbPassword());
            if (connection==null) System.out.println("Sorry but null");
            else System.out.println("Connection: "+ connection.getMetaData().getDatabaseProductName());
            return connection;
        }
        catch (SQLException e){
            e.printStackTrace();
            logger.error("Connection not created");
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("Connection not created");
        }
        return null;
    }

    public Connection getH2Connection() {
        try {
            logger.info("Try to get H2 connection");
            String url = "jdbc:h2:./h2db";
            String name = config.getDbLogin() ;
            String pass = config.getDbLogin();

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
