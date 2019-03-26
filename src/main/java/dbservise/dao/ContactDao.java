package dbservise.dao;


import dbservise.dao.dbentities.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactDao extends Dao<Contact> {

    private final static String CONTACT_TABLE = "user_contacts";
    private final static String OWNER_COLUMN = "user1_id";
    private final static String CONTACT_COLUMN = "user2_id";

    public ContactDao(Connection connection) {
        super(connection);
    }

    @Override
    Contact parseResult(ResultSet resultSet) throws SQLException {
        return new Contact(resultSet.getLong(1), resultSet.getLong(2), resultSet.getLong(3));
    }

    @Override
    String getInsertQuery() {
        return "INSERT INTO " + CONTACT_TABLE + " VALUES (?,?);";
    }

    @Override
    String getDeleteQuery() {
        return "DELETE FROM " + CONTACT_TABLE + " WHERE " + CONTACT_COLUMN + "=?;";
    }

    @Override
    void getInsertParamSetter(Contact contact, PreparedStatement statement) throws SQLException {
        statement.setLong(1, contact.getOwnerId());
        statement.setLong(2, contact.getFriendId());
    }

    @Override
    void getDeleteParamSetter(Contact contact, PreparedStatement statement) throws SQLException {
        statement.setLong(1, contact.getFriendId());

    }

    public List<Contact> getContactListByOwnerId(long ownerId) throws SQLException {
        return getListBy(CONTACT_TABLE, CONTACT_COLUMN, String.valueOf(ownerId));
    }

    public void insertContact(Contact contact) throws SQLException{
        insert(contact);
    }


   /* public Contact getContact(long ownerId, long contactId) throws SQLException {
        String query = "SELECT * FROM " + CONTACT_TABLE + " WHERE " + OWNER_COLUMN + "= (?) AND" +
                CONTACT_COLUMN + "=(?);";
        return executeSelect(query, statement -> {
            statement.setLong(1, ownerId);
            statement.setLong(2, contactId);
        }, resultSet -> {
            resultSet.next();
            return parseResult(resultSet);
        });
    }*/
}
