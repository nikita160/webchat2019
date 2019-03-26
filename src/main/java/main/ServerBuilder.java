package main;


import dbservise.ConnectionBuilderImpl;
import dbservise.DBAccountService;
import frontend.servlets.*;
import frontend.servlets.chat.PageChatServlet;
import frontend.servlets.chat.WebSocketChatServlet;
import main.config.ServerConfig;
import model.accountservice.AccountService;
import model.authservice.AuthService;
import model.authservice.SimpleAuthService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.parsing.xml.sax.ReadXMLFileSax;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class ServerBuilder {

    static ServerConfig config;

    private static final Logger logger = LogManager.getLogger(ServerBuilder.class);


    public static Logger getLogger(){
        return logger;
    }

    static void initServer(ServerConfig config) {
        ReadXMLFileSax.updateByXML(config, "."+System.getProperty("file.separator")+"server.cfg.xml");
        logger.info("Server properties installed");


        AccountService service = new DBAccountService(new ConnectionBuilderImpl(config));
        logger.info("Connection to DB successful");
        AuthService authService = new SimpleAuthService();
     ;

        try{
                        Server server = new Server(config.getServerPort());

            ServletContextHandler context = new ServletContextHandler(
                    ServletContextHandler.SESSIONS);

            //context.setContextPath("/");

            //context.addServlet(new ServletHolder(new SessionServlet()), "/session");
            context.addServlet(new ServletHolder(new AuthServlet(service, authService)), AuthServlet.PAGE_URL);
            context.addServlet(new ServletHolder(new SignUpServlet(service)), SignUpServlet.PAGE_URL);
            context.addServlet(new ServletHolder(new PageChatServlet(service)), PageChatServlet.PAGE_URL);
            context.addServlet(new ServletHolder(new LogOutServlet(service)), "/logout");
            context.addServlet(new ServletHolder(new WebSocketChatServlet(service)), "/chat/*");
            context.addServlet(new ServletHolder(new UserSearchServlet(service)), "/search");
            context.addServlet(new ServletHolder(new AddUserToContactServlet(service)), "/add-friend");

            ResourceHandler resourceHandler = new ResourceHandler();
            resourceHandler.setResourceBase("res_html");
            resourceHandler.setDirectoriesListed(true);

            HandlerList handlers = new HandlerList();
            handlers.setHandlers(new Handler[]{resourceHandler, context});

            server.setHandler(handlers);


            server.start();
            server.join();

        }

        catch (Exception e) {
            logger.error("Server start error");
            e.printStackTrace();
        }

    }

}
