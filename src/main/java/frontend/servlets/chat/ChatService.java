package frontend.servlets.chat;

import service.parsing.MessageFormatException;
import service.parsing.MessageParser;
import model.accountservice.AccountService;
import model.messageservice.Message;
import model.messageservice.MessageType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {

    private static  final String ALL_USERS_REQUEST = "all";
    private final AccountService accountService;
    private Map<String, Set<ChatWebSocket>> userNametoWebSocketSet;



    public ChatService(AccountService accountService) {
        //this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
        userNametoWebSocketSet = new ConcurrentHashMap<>();
        this.accountService = accountService;
    }

    public void sendMessage(String jsonStr) {
        Message message = null;
        try{
            message = MessageParser.getMessage(jsonStr);
        }
            catch (MessageFormatException e){
        }

        if (message.getType()==MessageType.LOGIN) System.out.println("***LOGON MESSGAE!");
        if (message.getType()==MessageType.MESSAGE) System.out.println("***SIMPLE MESSAGE!");
        if (message==null||message.getType()!= MessageType.MESSAGE) return;

        Set<ChatWebSocket> webSocketSet;

        if (message.getTo().equals(ALL_USERS_REQUEST)) {
            Iterator<Map.Entry<String, Set<ChatWebSocket>>> iterator = userNametoWebSocketSet.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Set<ChatWebSocket>> entry = iterator.next();
                webSocketSet = entry.getValue();
                sendStringToAllSet(webSocketSet,jsonStr);

            }
        }
        else {
            System.out.println ("Message vrom: "+message.getFrom());
            //webSocketSet = userNametoWebSocketSet.get(message.getFrom());
            //sendStringToAllSet(webSocketSet, jsonStr);
            try{
                if (accountService.isFriend(message.getFrom(), message.getTo())){
                    message.setIsByFriend(true);
                    System.out.println("BOO");
                    jsonStr=MessageParser.strigifyMessage(message);
                    System.out.println(jsonStr);
                }
            }
            catch (Exception e){
                System.err.println("*Check friend exception*");
                e.printStackTrace();
            }

            webSocketSet = userNametoWebSocketSet.get(message.getTo());
            if (webSocketSet!=null) sendStringToAllSet(webSocketSet,jsonStr);
        }

    }

    public void add(ChatWebSocket webSocket) {

        Set<ChatWebSocket> webSocketSet= userNametoWebSocketSet.get(webSocket.getUserName());
        if (webSocketSet==null){
            System.out.println("HERE userid: "+webSocket.getUserName()+"NULL");
            webSocketSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
            webSocketSet.add(webSocket);
            System.out.println("size:::" + webSocketSet.size());
            userNametoWebSocketSet.put(webSocket.getUserName(), webSocketSet);
            System.out.println(userNametoWebSocketSet.get(webSocket.getUserName())+"gggggggg");
        }
        else webSocketSet.add(webSocket);
    }

    public void remove(ChatWebSocket webSocket) {
        Set<ChatWebSocket> webSocketSet= userNametoWebSocketSet.get(webSocket.getUserName());
        webSocketSet.remove(webSocket);
        if (webSocketSet.size()==0) userNametoWebSocketSet.remove(webSocket.getUserName());

    }

    void sendStringToAllSet (Set<ChatWebSocket> webSocketSet, String msg){

        for (ChatWebSocket socket: webSocketSet){
            socket.sendString(msg);
        }
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
