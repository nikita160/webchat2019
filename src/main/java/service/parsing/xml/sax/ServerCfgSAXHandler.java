package service.parsing.xml.sax;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ServerCfgSAXHandler extends DefaultHandler {

    private String currentElement;
    private Object object;

    public ServerCfgSAXHandler (Object object){
        this.object=object;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parsing");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End parsing");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        //System.out.println("Parse element: "+qName);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        qName = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentElement != null) {
            String value = new String(ch, start, length);
            if (!value.matches("\\s*")){
                //System.out.println(currentElement + " = " + value);
                ReflectionHelper.setFieldValue(object, currentElement, value);
            }

        }
    }

    public Object getObject() {
        return object;
    }
}
