package mytest;

import model.accountservice.AccountService;
import model.accountservice.UserAccountDataSet;

import java.util.*;


public class AccountServiceFake /*implements AccountService*/ {

  /*  Map<String, UserAccountDataSet> userByLogin = new HashMap();
    Map<String, String> userTokenToLogin = new HashMap<>();

    private final boolean ANONYMOUS_POLICY = true;

    public AccountServiceFake(){
        userByLogin.put("admin", new UserAccountDataSet("admin", "admin") );
        userByLogin.put("root", new UserAccountDataSet("root", "root"));
        userByLogin.put("cat", new UserAccountDataSet("cat", "cat"));
        userByLogin.put("dog", new UserAccountDataSet("dog", "dog"));


        addContactToUserContactList("admin", "root");
        addContactToUserContactList("admin", "dog");
        addContactToUserContactList("admin", "cat");


    }

   /* public AccountServiceFake(){
        singUp("admin", "admin");
    }

   @Override
    public void singUp(String name, String password) {
    }

    @Override
    public boolean singIn(String name, String password) {
        if (name!="admin")
        return false;
    }*/

/*    @Override
    public void createNewUser(UserAccountDataSet user) {
        userByLogin.put(user.getName(), user);
    }

    @Override
    public UserAccountDataSet getUserByLogin(String login) {
        return userByLogin.get(login);
    }


  /*  @Override
    public boolean getAnonymousPolicy() {
        return ANONYMOUS_POLICY;
    }*/
/*
    @Override
    public String getUserByToken(String token) {
        return userTokenToLogin.get(token);
    }

    @Override
    public void setUserToken(String token, String login) {
        userTokenToLogin.put(token, login);
    }

    @Override
    public void deleteToken(String token) {
        userTokenToLogin.remove(token);
    }

    @Override
    public boolean isFriend(String userName, String whichFriendUserName) {
        List<String> contact = getUserByLogin(whichFriendUserName).getContactList();
        if (contact.contains(userName)) return true;
        else return false;

        /*for (String contactName: contact){
            if (contactName==userName) return true;
        }*/

//    }
/*
    @Override
    public List<String> searchUser(String query, String currentUserName) {

        List<String> res = new ArrayList<>();
        Set<String> keySet = userByLogin.keySet();
        Iterator<String> iterator = keySet.iterator();
        String temp;
        String regex = "\\w*"+query+"\\w*";

        while (iterator.hasNext()){
            temp = iterator.next();
            if (temp.matches(regex)&&!(temp.equals(currentUserName))) res.add(temp);
        }
        res.removeAll(userByLogin.get(currentUserName).getContactList());
        return (res.size()>0)?res:null;
    }


    @Override
    public int addContactToUserContactList(String contactListOwnerLogin, String contactToAddLogin) {
        UserAccountDataSet userOwner = this.getUserByLogin(contactListOwnerLogin);
        UserAccountDataSet userToAdd = this.getUserByLogin(contactToAddLogin);
        if (userOwner==null||userToAdd==null) return -1;
        userOwner.getContactList().add(contactToAddLogin);
        return 1;
    }*/


}
