package dbservise.dao.dbentities;


public class Contact {

    private final long id;
    private final long ownerId;
    private final long friendId;


    public Contact(long ownerId, long friendId) {
        this(-1, ownerId, friendId);
    }

    public Contact(long id, long ownerId, long friendId) {
        this.id = id;
        this.ownerId = ownerId;
        this.friendId = friendId;
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public long getFriendId() {
        return friendId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", friendId=" + friendId +
                '}';
    }
}
