package domain;

import java.util.*;

public class InfoBank {
    public static String apiKey = "c32ef950-17c2-11e8-87b4-496f79ef1988";
    private static ArrayList<User> users = new ArrayList<User>();
    public static Estate estate = new Estate();

    static {
        users.add(new Individual("بهنام همایون","09123456789","بهنام همایون","1234"));
        users.add(new RealEstate("بنگاه اول", "09123456789"));
    }

    public static ArrayList<User> getUsers(){
        return users;
    }

    public static Individual getUserByUsername(String username){
        for(int i=0; i<users.size(); i++) {
            if (users.get(i) instanceof Individual) {
                Individual individual = (Individual)users.get(i);
                if (individual.getUsername().equals(username))
                    return individual;
            }
        }
        //user not found!
        return null;
    }

    public static User getUserByName(String name){
        for(int i=0; i<users.size(); i++) {
            if (users.get(i) instanceof RealEstate) {
                RealEstate realEstate = (RealEstate) users.get(i);
                if (realEstate.getName().equals(name))
                    return users.get(i);
            }
        }
        //user not found!
        return null;
    }
}
