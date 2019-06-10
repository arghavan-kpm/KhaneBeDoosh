package domain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class InfoBank {
    public static String apiKey = "c32ef950-17c2-11e8-87b4-496f79ef1988";
    public static Estate estate = new Estate();

    static {
        ORMapper.CreateUserTable();
        ORMapper.CreateRealEstateTable();

        ORMapper.CreateHouseTable();

        ORMapper.CreateUserOwnRel();
        ORMapper.CreateRealEOwnRel();
        ORMapper.CreatePaidRel();


        ORMapper.InsertUser(new Individual("salam","09123456789","salam","1234", 1));
        ORMapper.InsertUser(new Individual("کوثر پوراحمدی","123456789","کوثر","4321",0));
        ORMapper.InsertRealE(new RealEstate("بنگاه اول", "09123456789"));

        try {

            ORMapper.FillHouses(ORMapper.SelectRealE("بنگاه اول"));

        }catch(Exception e){System.out.println("Fill houses error : " + e.getClass().getName() + ": " + e.getMessage());}


    }


    public static Individual getUserByUsername(String username){
        return ORMapper.SelectUser(username);
    }

    public static User getUserByName(String name){
        return ORMapper.SelectRealE(name);

    }

    public static JSONArray getUserHouses(String userName){
        ArrayList<String> res = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray();

        res = ORMapper.SelectPaidRelForInd( getUserByUsername(userName));
        for(int i=0; i<res.size(); i++){
            JSONObject obj = new JSONObject();
            obj.put("house_id", res.get(i));
            jsonArray.put(obj);
        }

        return jsonArray;
    }

    public static JSONArray getAllUsersHouses(){
        Map<String, ArrayList<String> > map = ORMapper.SelectAllPaidRel();

        JSONArray res = new JSONArray();

        Set set=map.entrySet();//Converting to Set so that we can traverse

        Iterator itr=set.iterator();

        while(itr.hasNext()){
            JSONArray jsonArray = new JSONArray();
            Map.Entry entry=(Map.Entry)itr.next();
            JSONObject obj = new JSONObject();

            obj.put("userName",entry.getKey());

            ArrayList<String> houses = (ArrayList<String>)entry.getValue();

            for(int i=0; i< houses.size(); i++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("house_id", houses.get(i));
                jsonArray.put(jsonObject);
            }

            obj.put("houses",jsonArray);

            res.put(obj);
        }
        return res;
    }
}
