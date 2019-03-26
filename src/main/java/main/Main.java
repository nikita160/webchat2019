package main;

import main.config.ServerConfig;
import service.VFS;
import service.VFSImpl;
import service.parsing.xml.sax.ReadXMLFileSax;

import java.io.*;
import java.util.Iterator;

public class Main {



    public static void main (String[] args){

        System.setProperty("log4j.configurationFile","./log4j2.xml");
      //  System.out.println(System.getProperty("log4j.configurationFile"));
        ServerBuilder.initServer(new ServerConfig());
       // ServerConfig config = new ServerConfig();
        //System.out.println(config);




    }

}
