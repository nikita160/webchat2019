package service.parsing.xml.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "list")
public class ListJAXB<T> {

    @XmlElement(name="item")
    private List<T> list;

    public ListJAXB() {}

    public ListJAXB(List<T> list){
        this.list = list;
    }

    public List<T> getList(){
        return list;
    }

}
