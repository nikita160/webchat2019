package frontend.servlets.chat;

import model.accountservice.AccountService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;


//@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends WebSocketServlet {
    private  ChatService chatService;

    public WebSocketChatServlet(AccountService accountService){
        this.chatService = new ChatService(accountService);


    }

    @Override
    public void configure(WebSocketServletFactory factory) {

        // set a timeout
        factory.getPolicy().setIdleTimeout(10 * 60 * 1000);

        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));

    }




}
