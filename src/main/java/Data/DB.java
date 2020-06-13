package Data;

import models.User;


import java.util.ArrayList;

public class DB{
    private static DB instance;
    private ArrayList<User> users;

    private DB(){
    }

    public static DB getInstance(){
        if(instance ==  null){
            instance =  new DB();
            instance.init();
        }

        return instance;
    }

    public void init(){
        users = new ArrayList<User>();
        users.add(new User("Student1","pass"));
        users.add(new User("Student2","pass"));
        users.add(new User("Student3","pass"));

    }

    public  User findByUsername(String username) {
        if (username == null) return null;

        User found = null;
        for (User user: users){
            if(username.equals(user.getUsername())){
                found = user;
            }
        }
        return found;
    }

}
