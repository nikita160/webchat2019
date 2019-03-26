package model.accountservice.datasets;


import java.util.List;

public class MainPageDataSet {

    private List<String> contactList;

    public MainPageDataSet(List<String> contactList){
        this.contactList=contactList;
    }

    public List<String> getContactList() {
        return contactList;
    }
}
