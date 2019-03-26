package dbservise.dao.dbentities;

public class Token {
    private final long id;
    private final String value;
    private final String userName;

    public Token (long id, String value, String userName){
        this.id = id;
        this.value = value;
        this.userName = userName;
    }

    public Token(String value, String userName) {
        this.id = -1;
        this.value = value;
        this.userName = userName;
    }

    public String getValue() {
        return value;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
