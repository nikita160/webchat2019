package service.parsing.xml.jaxb;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

public class JAXBHandler {



    public static String getXMLFromList (List<String> list){
        if (list.size()==0) return null;
        ListJAXB<String> listJAXB = new ListJAXB(list);
        StringWriter writer = new StringWriter();

        try{
            JAXBContext context = JAXBContext.newInstance(ListJAXB.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "");

            marshaller.marshal(listJAXB, writer);
        }
        catch (JAXBException e ){
            return null;
        }

        return writer.toString();

    }

}
