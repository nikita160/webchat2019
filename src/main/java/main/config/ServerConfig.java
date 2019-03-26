package main.config;


public class ServerConfig{
    String dbDriver;
    int dbPort = 3306;
    String dbLogin;
    String dbPassword;
    String dbName;
    int serverPort = 8080;
    boolean prefillDB;

    public int getDbPort() {
        return dbPort;
    }

    public String getDbLogin() {
        return dbLogin;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbName() {
        return dbName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public boolean isPrefillDB() {
        return prefillDB;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "dbdriver='" + dbDriver + '\'' +
                ", dbPort=" + dbPort +
                ", dbLogin='" + dbLogin + '\'' +
                ", dbPassword='" + dbPassword + '\'' +
                ", dbName='" + dbName + '\'' +
                ", serverPort=" + serverPort +
                ", prefillDB=" + prefillDB +
                '}';
    }
}
