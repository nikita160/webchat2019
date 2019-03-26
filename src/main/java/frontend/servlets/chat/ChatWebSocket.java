package frontend.servlets.chat;


import model.accountservice.AccountService;
import org.eclipse.jetty.websocket.api.Session;

import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


import java.io.IOException;
import java.net.URI;


@SuppressWarnings("UnusedDeclaration")
@WebSocket
public class ChatWebSocket {

    private AccountService accountService;
    private ChatService chatService;
    private Session session;
    private String userName;

    public ChatWebSocket(ChatService chatService) {
        this.chatService = chatService;
        accountService = chatService.getAccountService();

    }


    @OnWebSocketConnect
    public void onOpen(Session session) throws IOException{
        this.session = session;
        URI requestURI = session.getUpgradeRequest().getRequestURI();
        String userToken = getTokenFromRequest(requestURI);
        System.out.println(userToken);
        try {
            this.userName = accountService.getLoginByToken(userToken);
            chatService.add(this);
            System.out.println(userToken+"**"+userName);
            if (userToken ==null||accountService.getLoginByToken(userToken)==null) {
                session.close(403, "no UUID");
            }
            System.out.println("Socket created" );

        }
        catch (Exception e){
            System.err.println("*Socket not created*");
        }

    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        chatService.sendMessage(msg);

    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        chatService.remove(this);
        //System.out.println("Socket closed ");
    }


    public void sendString(String data) {
        try {
            session.getRemote().sendString(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

   /* static String getCookieValueByName (List<HttpCookie> cookies, String name){
        System.out.println("tryto fing cookie");
        if (cookies!=null) {
            for (HttpCookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }*/

    String getUserName() {
        return userName;
    }

    String getTokenFromRequest (URI uri){
        String path = uri.getPath();
        String idStr = path.substring(path.lastIndexOf('/')+1);
        return idStr;
    }
}
