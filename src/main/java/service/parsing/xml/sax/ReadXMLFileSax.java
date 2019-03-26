package service.parsing.xml.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ReadXMLFileSax {

    public static Object updateByXML (Object object, String xmlFile){
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            ServerCfgSAXHandler handler = new ServerCfgSAXHandler(object);
            parser.parse(xmlFile, handler);
            return handler.getObject();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }
}
