package service.parsing;

import model.messageservice.Message;

public class MessageParser {

    private static final Serializer<Message> msgSerializer = new SerializerJSON(Message.class);

    public static Message getMessage(String data) throws MessageFormatException{
        Message msg = msgSerializer.deserialize(data);
        if (msg.getType()==null) {
            throw new MessageFormatException();
        }
        else return msg;
    }

    public static String strigifyMessage (Message msg){
        return msgSerializer.serialize(msg);
    }

}
