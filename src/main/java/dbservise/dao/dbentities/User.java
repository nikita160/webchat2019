package dbservise.dao.dbentities;

import java.util.ArrayList;
import java.util.List;


public class User {

    private long id;

    private String name;

    private String password;



    /*public UserAccountDataSet (long id, String name, String password, List<UserAccountDataSet> list){
        this (id, name, password);
        contactList = list;
    }*/



    public User(long id, String name, String password){
        this.id = id;
        this.name = name;
        this. password = password;
    }

    public User(String name, String password){
        this (-1, name, password);
    }


    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserAccountDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
