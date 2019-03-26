package model.accountservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class UserAccountDataSet{

    private  List<String> contactList;
    private Set<String> tokenSet;

    public UserAccountDataSet (List<String> contactList){
        this.contactList = contactList;
    }

    public List<String> getContactList() {
        return contactList;
    }
}
