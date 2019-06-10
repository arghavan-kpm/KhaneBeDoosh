package domain;

import org.json.JSONArray;
import org.json.JSONObject;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ORMapper {

    private static  Connection connect2DB(){

        /*
        PoolProperties p = new PoolProperties();
          p.setUrl("jdbc:mysql://localhost:3306/mysql");
          p.setDriverClassName("com.mysql.jdbc.Driver");
          p.setUsername("root");
          p.setPassword("password");
          p.setJmxEnabled(true);
          p.setTestWhileIdle(false);
          p.setTestOnBorrow(true);
          p.setValidationQuery("SELECT 1");
          p.setTestOnReturn(false);
          p.setValidationInterval(30000);
          p.setTimeBetweenEvictionRunsMillis(30000);
          p.setMaxActive(100);
          p.setInitialSize(10);
          p.setMaxWait(10000);
          p.setRemoveAbandonedTimeout(60);
          p.setMinEvictableIdleTimeMillis(30000);
          p.setMinIdle(10);
          p.setLogAbandoned(true);
          p.setRemoveAbandoned(true);
          p.setJdbcInterceptors(
            "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
          DataSource datasource = new DataSource();
          datasource.setPoolProperties(p);

          Connection con = null;
          try {
            con = datasource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from user");
            int cnt = 1;
            while (rs.next()) {
                System.out.println((cnt++)+". Host:" +rs.getString("Host")+
                  " User:"+rs.getString("User")+" Password:"+rs.getString("Password"));
            }
            rs.close();
            st.close();
          } finally {
            if (con!=null) try {con.close();}catch (Exception ignore) {}
        }

         */

        Connection c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");

        return  c;
    }       ///TODO: POOL


    public static void FillHouses(RealEstate real) throws Exception{   // همان موقع که خانه ها را اضافه می کنیم رابطه ی مالکیت با بنگاه را هم قرار می دهیم
        JSONObject obj = new JSONObject(Tools.GETreq("http://139.59.151.5:6664/khaneBeDoosh/v2/house"));

        Connection c = connect2DB();
        Statement stmt = null;

        String sql = "DELETE FROM HOUSE WHERE EXPIRETIME <> '';\n";
        System.err.println(obj);
        JSONArray arr = obj.getJSONArray("data");
        Object EXPT = obj.get("expireTime");
        Long expt =  (Long)EXPT;
        for(int i=0; i<arr.length(); i++){
            String id = arr.getJSONObject(i).getString("id");
            int area = arr.getJSONObject(i).getInt("area");
            JSONObject price = arr.getJSONObject(i).getJSONObject("price");
            int sellP=0;
            int rentP=0;
            int baseP=0;

            if(price.has("sellPrice")){
                sellP = price.getInt("sellPrice");
            }
            else{
                rentP = price.getInt("rentPrice");
                baseP = price.getInt("basePrice");
            }
            int dealT = arr.getJSONObject(i).getInt("dealType");
            String buildT = arr.getJSONObject(i).getString("buildingType");
            String image = arr.getJSONObject(i).getString("imageURL");
            String addr = arr.getJSONObject(i).getString("address");
            sql += "INSERT OR REPLACE INTO HOUSE (HOUSEID,AREA,BUILDINGTYPE,ADDRESS,PHONE,IMGURL,DESCRIPTION,DEALTYPE,SELLP,RENTP, BASEP , EXPIRETIME)"
                            +"VALUES ( '" + id + "','" + area + "','" + buildT + "','" + addr + "','" + "" + "','" + image + "','"
                            + "" + "','" + dealT + "'," + sellP + "," + rentP + "," +  baseP + ",'" + expt.toString() + "'); \n";
            sql += "INSERT OR REPLACE INTO REALEOWN (HOUSEID, NAME)"
                    + "VALUES ( '" +id+ "','" + real.getName() + "'); \n";


        }

        try {

            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.commit();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


        stmt.close();
        c.close();

    }


    public static void CreateUserTable( ) { // User = Individual
        Connection c = connect2DB();
        Statement stmt = null;

        try {

            stmt = c.createStatement();

            String sql =
                    " CREATE TABLE IF NOT EXISTS USER " +
                    "(USERNAME TEXT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PHONE            TEXT     NOT NULL, " +
                    " PASSWORD        TEXT NOT NULL, " +
                    " CREDIT         INT  NOT NULL," +
                    " IS_ADMIN       INT  NOT NULL );";


            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("User Table created successfully");
    }


    public static void CreateRealEstateTable( ) {
        Connection c = connect2DB();
        Statement stmt = null;

        try {

            stmt = c.createStatement();

            String sql =
                    " CREATE TABLE IF NOT EXISTS REALESTATE " +
                            "(NAME             TEXT PRIMARY KEY     NOT NULL," +
                            " PHONE            TEXT     NOT NULL );";


            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Real Estate Table created successfully");
    }


    public static void CreatePaidRel( ) {
        Connection c = connect2DB();
        Statement stmt = null;

        try {

            stmt = c.createStatement();

            String sql =

                    "CREATE TABLE IF NOT EXISTS PAID " +
                    "(USERNAME TEXT      NOT NULL," +
                    " HOUSEID  TEXT      NOT NULL ," +
                    "PRIMARY KEY (USERNAME, HOUSEID)," +
                    "FOREIGN KEY(USERNAME) REFERENCES USER(USERNAME), " +
                    "FOREIGN KEY(HOUSEID) REFERENCES USER(HOUSE) );";



            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Paid rel Table created successfully");
    }


    public static void CreateUserOwnRel( ) {
        Connection c = connect2DB();
        Statement stmt = null;

        try {

            stmt = c.createStatement();

            String sql =
                    "CREATE TABLE IF NOT EXISTS USEROWN " +

                    "(HOUSEID TEXT      NOT NULL," +
                    "USERNAME   TEXT      NOT NULL, " +
                    "PRIMARY KEY (HOUSEID, USERNAME)," +
                    "FOREIGN KEY(HOUSEID) REFERENCES HOUSE (HOUSEID)," +
                    "FOREIGN KEY(USERNAME) REFERENCES USER(USERNAME) );";




            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Own Rel Table created successfully");
    }


    public static void CreateRealEOwnRel( ) {
        Connection c = connect2DB();
        Statement stmt = null;

        try {

            stmt = c.createStatement();

            String sql =
                    "CREATE TABLE IF NOT EXISTS REALEOWN " +
                            "(HOUSEID TEXT      NOT NULL," +
                            "NAME   TEXT       NOT NULL, " +
                            "PRIMARY KEY (HOUSEID, NAME),"+
                            "FOREIGN KEY(HOUSEID) REFERENCES HOUSE (HOUSEID)," +
                            "FOREIGN KEY(NAME) REFERENCES REALESTATE(NAME ) );";




            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Own Rel Table created successfully");
    }


    public static void CreateHouseTable( ) {
        Connection c = connect2DB();
        Statement stmt = null;

        try {

            stmt = c.createStatement();

            String sql =
                    " CREATE TABLE IF NOT EXISTS HOUSE " +
                    "(HOUSEID       TEXT            PRIMARY KEY     NOT NULL," +
                    " AREA          INTEGER         NOT NULL, " +
                    " BUILDINGTYPE  TEXT            NOT NULL, " +
                    " ADDRESS       TEXT            NOT NULL, " +
                    " PHONE         TEXT            NOT NULL," +
                    " IMGURL        TEXT            NOT NULL," +
                    " DESCRIPTION   TEXT            NOT NULL," +
                    " DEALTYPE      INTEGER            NOT NULL ," +
                    " SELLP         INTEGER         NOT NULL ," +
                    " RENTP         INTEGER         NOT NULL ," +
                    " BASEP         INTEGER         NOT NULL, "+
                    "EXPIRETIME     TEXT            NOT NULL) ;";


            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("House Table created successfully");
    }


    public static void InsertHouse( House house) { //if Real , flag = 1
        Connection c = connect2DB();
        Statement stmt = null;


        try {

            stmt = c.createStatement();
            String sql =
                    "INSERT OR REPLACE INTO HOUSE (HOUSEID,AREA,BUILDINGTYPE,ADDRESS,PHONE,IMGURL,DESCRIPTION,DEALTYPE,SELLP,RENTP, BASEP , EXPIRETIME)"
                    +"VALUES ( '" + house.getId() + "','" + house.getArea() + "','" + house.getBuildType()+
                            "','" + house.getAddr() + "','" + house.getOwnerPhone() + "','" + house.getImageURL() + "','" + house.getDesc() +
                            "','" + house.getDeal() + "'," + house.getSellP() + "," + house.getRentP() + "," + house.getBaseP()+ ",'" + house.getExpireTime() + "');";

            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.print("Records of IndHouse created :");
    }


    public static void InsertUser( Individual ind ) {
        Connection c = connect2DB();
        Statement stmt = null;


        try {

            stmt = c.createStatement();
            String sql =
                    "INSERT OR REPLACE INTO USER (USERNAME,NAME,PHONE,PASSWORD,CREDIT, IS_ADMIN) " + //replace will be removed
                    "VALUES ( '" + ind.getUsername() + "','" + ind.getName() + "','" + ind.getPhone()+"','" + ind.getPassword() + "'," + ind.getCredit() + "," + ind.getIsAdmin() + ");";

            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.print("User Records Inserted :");
    }


    public static void InsertRealE( RealEstate real ) {
        Connection c = connect2DB();
        Statement stmt = null;


        try {

            stmt = c.createStatement();
            String sql =
                    "INSERT OR REPLACE INTO REALESTATE (NAME,PHONE) " + //replace will be removed
                            "VALUES ( '" + real.getName() + "','" + real.getPhone() + "');";

            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.print("RealE Records Inserted :");
    }


    public static void InsertUserOwnRel(Individual ind, House house){
        Connection c = connect2DB();
        Statement stmt = null;


        try {

            stmt = c.createStatement();
            String sql =
                    "INSERT OR REPLACE INTO USEROWN (HOUSEID, USERNAME) " + //replace will be removed
                            "VALUES ( '" + house.getId() + "','" + ind.getUsername() + "');";

            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.print("UserOwnRel Records Inserted :");
    }


    public static void InsertPaidRel(Individual ind, House house){
        Connection c = connect2DB();
        Statement stmt = null;


        try {

            stmt = c.createStatement();
            String sql =
                    "INSERT OR REPLACE INTO PAID ( USERNAME, HOUSEID) " + //replace will be removed
                            "VALUES ( '" + ind.getUsername() + "','" + house.getId() + "');";

            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.print("PaidRel Records Inserted :");
    }


    public static boolean SelectPaidRel(Individual ind, House house){
        Connection c = connect2DB();
        Statement stmt = null;
        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PAID p WHERE p.USERNAME='" + ind.getUsername() + "' and " +
                    " p.HOUSEID ='" + house.getId() + "';" );
            c.commit();

            if ( rs.next() ) {
                rs.close();
                stmt.close();
                c.close();
                System.out.println(" Paid Rel Selected ");
                return true;
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


        return false;
    }


    public static ArrayList<String> SelectPaidRelForInd(Individual ind){
        Connection c = connect2DB();
        Statement stmt = null;
        ArrayList<String> res = new ArrayList<String>();
        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PAID p WHERE p.USERNAME='" + ind.getUsername() + "' ;" );
            c.commit();

            while(rs.next()){
                String house_id = rs.getString("HOUSEID");
                res.add(house_id);
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


        return res;
    }


    public static Map<String, ArrayList<String> > SelectAllPaidRel(){
        Connection c = connect2DB();
        Statement stmt = null;
        Map<String, ArrayList<String> > map = new HashMap< String, ArrayList<String> >();

        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PAID p ;" );
            c.commit();

            while ( rs.next() ){
                String userName = rs.getString("USERNAME");
                String house_id = rs.getString("HOUSEID");
                if(map.get(userName) == null){
                    map.put(userName, new ArrayList<String>());
                }
                map.get(userName).add(house_id);
            }

            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


        return map;
    }


    public static Individual SelectUser( String username ) {

        if(username.contains("\'")) {
            System.out.println(" *** INJECTION ATTEMPT CATCHED ***");
            return null;
        }

        Individual ind = null;
        Connection c = connect2DB();
        Statement stmt = null;
        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USER u WHERE u.USERNAME='" + username + "';" );
            c.commit();
            while ( rs.next() ) {

                String userName = rs.getString("USERNAME");
                String name = rs.getString("NAME");
                String phone = rs.getString("PHONE");
                String pass = rs.getString("PASSWORD");
                Integer credit = rs.getInt("CREDIT");
                Integer is_admin = rs.getInt("IS_ADMIN");

                ind = new Individual(name, phone, userName, pass, credit, is_admin);
                System.out.print("Current : ");
                System.out.println(ind);
            }
            rs.close();
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println(" User Selected ");

        return ind;
    }


    public static RealEstate SelectRealE( String name ) {

        if(name.contains("\'")) {
            System.out.println(" *** INJECTION ATTEMPT CATCHED ***");
            return null;
        }

        RealEstate real = null;
        Connection c = connect2DB();
        Statement stmt = null;
        try {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM REALESTATE r WHERE r.NAME ='" + name + "';" );
            c.commit();

            while ( rs.next() ) {

                String Name = rs.getString("NAME");
                String phone = rs.getString("PHONE");

                real = new RealEstate(Name, phone);
            }
            rs.close();
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println(" RealEstate Selected ");

        return real;
    }


    public static House SelectHouse(String id){

        if(id.contains("\'")) {
            System.out.println(" *** INJECTION ATTEMPT CATCHED ***");
            return null;
        }

        Connection c = connect2DB();
        Statement stmt = null;
        House house = null;

        try {

            stmt = c.createStatement();
            String sql = "SELECT * FROM HOUSE ie " +
                    "WHERE ie.HOUSEID = '" + id + "';" ;


            ResultSet rs = stmt.executeQuery( sql );
            c.commit();
            while ( rs.next() ) {
                String houseId = rs.getString("HOUSEID");
                Integer area = rs.getInt("AREA");
                String buildType = rs.getString("BUILDINGTYPE");
                String addr = rs.getString("ADDRESS");
                String phone = rs.getString("PHONE");
                String imgUrl = rs.getString("IMGURL");
                String desc = rs.getString("DESCRIPTION");
                Integer dealT = rs.getInt("DEALTYPE");
                Integer sellP = rs.getInt("SELLP");
                Integer rentP = rs.getInt("RENTP");
                Integer baseP = rs.getInt("BASEP");
                String ExpT = rs.getString("EXPIRETIME");

                //Update House with empty columns

                if(desc.equals("")) {
                    JSONObject Obj = new JSONObject(Tools.GETreq(
                            "http://139.59.151.5:6664/khaneBeDoosh/v2/house/" + id));
                    System.out.println("URL = " + "http://139.59.151.5:6664/khaneBeDoosh/v2/house/" + id );
                    System.out.println("Object : " + Obj.toString());
                    JSONObject obj = Obj.getJSONObject("data");
                    phone = obj.getString("phone");
                    desc = obj.getString("description");

                    stmt = c.createStatement();
                    sql = "UPDATE HOUSE set PHONE = '"+ phone +"' , " +
                            "DESCRIPTION = '" + desc + "' where HOUSEID = '" + id + "';";

                    stmt.executeUpdate( sql );
                    c.commit();
                }


                house = new House(houseId, area, buildType, addr, imgUrl, dealT, baseP, rentP, sellP, phone, desc, ExpT, null);
            }


            rs.close();
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println(" House Selected ");

        return house;
    }


    public static ArrayList<House> SelectAllHouses(SearchForm sf){
        ArrayList<House> res = new ArrayList<House>();
        Connection c = connect2DB();
        Statement stmt = null;


        try {

            stmt = c.createStatement();
            String sql = "SELECT MAX(h.EXPIRETIME) AS MOSTRECENT " +
                         "FROM HOUSE h;";

            ResultSet rs = stmt.executeQuery( sql );
            c.commit();

            String EXPT = rs.getString("MOSTRECENT");
            System.out.println("NUMBER IS :  " + EXPT);
            Long expt = Long.parseLong(EXPT);

            System.out.print("EXPIRES AT : ");
            System.out.print(expt);
            System.out.print(" current time is : ");
            System.out.println(System.currentTimeMillis());

            if( expt < System.currentTimeMillis()){
                stmt.close();
                c.close();
                FillHouses(SelectRealE("بنگاه اول"));   // اگر بنگاه ها اضافه شدند باید روی RealEstate ها از دیتا بیس حلقه بزنیم
                c = connect2DB();
                stmt = c.createStatement();
            }

            sql = "SELECT * FROM HOUSE ie " +
                    "WHERE ie.AREA >= " + String.format("%d",sf.getMinArea()) + " and " ;

            if(!sf.getBuildType().equals(""))
                sql += "ie.BUILDINGTYPE = '" + sf.getBuildType() + "' and ";

            if(sf.getDeal() == 0)
                sql += "ie.DEALTYPE = 0 and ie.SELLP <= " + String.format("%d",sf.getMaxP()) + ";" ;
            if(sf.getDeal() == 1)
                sql += "ie.DEALTYPE = 1 and ie.RENTP <= " + String.format("%d",sf.getMaxP()) + ";";
            if(sf.getDeal() == -1)
                sql += "((ie.DEALTYPE = 0 and ie.SELLP <= " + String.format("%d",sf.getMaxP()) + ") or " +
                        "(ie.DEALTYPE = 1 and ie.RENTP <= " + String.format("%d",sf.getMaxP()) + "));" ;

            rs = stmt.executeQuery( sql );
            c.commit();
            while ( rs.next() ) {
                String houseId = rs.getString("HOUSEID");
                Integer area = rs.getInt("AREA");
                String buildType = rs.getString("BUILDINGTYPE");
                String addr = rs.getString("ADDRESS");
                String phone = rs.getString("PHONE");
                String imgUrl = rs.getString("IMGURL");
                String desc = rs.getString("DESCRIPTION");
                Integer dealT = rs.getInt("DEALTYPE");
                Integer sellP = rs.getInt("SELLP");
                Integer rentP = rs.getInt("RENTP");
                Integer baseP = rs.getInt("BASEP");
                String ExpT = rs.getString("EXPIRETIME");
                res.add(new House(houseId, area, buildType, addr, imgUrl, dealT, baseP, rentP, sellP, phone, desc, ExpT, null));
            }


            rs.close();
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println(" Houses Selected ");

        return res;
    }


    public static void UpdateUserCredit( Individual ind ) {

        Connection c = connect2DB();
        Statement stmt = null;

        try {

            stmt = c.createStatement();
            String sql = "UPDATE USER set CREDIT = " + ind.getCredit() + " where USERNAME = '" + ind.getUsername()+ "';";
            stmt.executeUpdate(sql);
            c.commit();

            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("User Credit Updated");
    }
}