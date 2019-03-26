package model.messageservice;



public class Message {

    private MessageType type;

    private String body;
    private String from;
    private String to;

    private boolean isByFriend = false;

    public Message(MessageType type, String from) {
        this.type = type;
        this.from = from;
    }

    public Message (MessageType type){
        this.type = type;
    }

    public Message (){
        this (MessageType.MESSAGE);
    }

    public static Message getInitMessage (String name){
        return new Message(MessageType.LOGIN, name);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MessageType getType() {
        return type;
    }

    public String getBody() {
        return body;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public boolean isByFriend() {
        return isByFriend;
    }

    public void setIsByFriend(boolean isByFriend) {
        this.isByFriend = isByFriend;
    }
}
