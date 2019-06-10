package domain;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.io.*;
import java.util.UUID;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

public class Tools{

	private static String secret = "mozi-amoo";

	public Tools(){

	}

	public static boolean IsItNum(String num){
		boolean flag = false;
		for(int i=0; i<num.length(); i++){
			if( (num.charAt(i) == '۰' || num.charAt(i) == '۱' || num.charAt(i) == '۲' || num.charAt(i) == '۳'
				|| num.charAt(i) == '۴' || num.charAt(i) == '۵' || num.charAt(i) == '۶' || num.charAt(i) == '۷'
				|| num.charAt(i) == '۸' || num.charAt(i) == '۹'
				|| ('0' <= num.charAt(i) && num.charAt(i) <= '9')))
				flag = true;
			else
				return  false;
		}
		return flag;
	}

	public static int decode(String num){
		if(num == null || num.equals(""))	return 0;
		StringBuilder myNum = new StringBuilder(num);
		for(int i=0; i<myNum.length(); i++){
			if(myNum.charAt(i) == '۰')
				myNum.setCharAt(i, '0');
			if(myNum.charAt(i) == '۱')
				myNum.setCharAt(i, '1');
			if(myNum.charAt(i) == '۲')
				myNum.setCharAt(i,'2');
			if(myNum.charAt(i) == '۳')
				myNum.setCharAt(i, '3');
			if(myNum.charAt(i) == '۴')
				myNum.setCharAt(i, '4');
			if(myNum.charAt(i) == '۵')
				myNum.setCharAt(i, '5');
			if(myNum.charAt(i) == '۶')
				myNum.setCharAt(i, '6');
			if(myNum.charAt(i) == '۷')
				myNum.setCharAt(i, '7');
			if(myNum.charAt(i) == '۸')
				myNum.setCharAt(i, '8');
			if(myNum.charAt(i) == '۹')
				myNum.setCharAt(i, '9');
		}
		return Integer.parseInt(myNum.toString());
	}

	public static String getPersian(String in){
		StringBuilder myIn = new StringBuilder(in);

		for(int i=0; i<myIn.length(); i++){
			if(myIn.charAt(i) == '0')
				myIn.setCharAt(i, '۰');
			if(myIn.charAt(i) == '1')
				myIn.setCharAt(i, '۱');
			if(myIn.charAt(i) == '2')
				myIn.setCharAt(i, '۲');
			if(myIn.charAt(i) == '3')
				myIn.setCharAt(i, '۳');
			if(myIn.charAt(i) == '4')
				myIn.setCharAt(i, '۴');
			if(myIn.charAt(i) == '5')
				myIn.setCharAt(i, '۵');
			if(myIn.charAt(i) == '6')
				myIn.setCharAt(i, '۶');
			if(myIn.charAt(i) == '7')
				myIn.setCharAt(i, '۷');
			if(myIn.charAt(i) == '8')
				myIn.setCharAt(i, '۸');
			if(myIn.charAt(i) == '9')
				myIn.setCharAt(i, '۹');
		}
		return myIn.toString();
	}

	public static String UUI(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String GETreq(String url) throws Exception{

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return  response.toString();

	}

	public static boolean POSTreq(String apiKey, String url, String UID, int cash)throws MalformedURLException, IOException{
		String json = "{\n\"userId\":\"" + UID + "\",\"value\":\""+ Integer.toString(cash)+ "\"\n}";

		FileWriter fstream = new FileWriter("log.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(json);

		URL myurl = new URL(url);
		HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);

		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("apiKey", apiKey);
		con.setRequestProperty("Method", "POST");
		OutputStream os = con.getOutputStream();
		os.write(json.toString().getBytes("UTF-8"));
		os.close();


		StringBuilder sb = new StringBuilder();
		int HttpResult =con.getResponseCode();
		if(HttpResult ==HttpURLConnection.HTTP_OK){
			BufferedReader br = new BufferedReader(new   InputStreamReader(con.getInputStream(),"utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			System.out.println(""+sb.toString());
			return true;

		}else{
			System.out.println(con.getResponseCode());
			System.out.println(con.getResponseMessage());
			return false;
		}
	}

	public static JSONObject User2JSON(User user){
		JSONObject obj = new JSONObject();
		obj.put("name", user.getName());
		obj.put("phone", user.getPhone());
		if( user instanceof Individual)
			obj.put("credit", ((Individual) user).getCredit());
		return obj;
	}

	public static JSONObject IndHouse2JSON(House house){
		JSONObject obj = House2JSON(house);
		obj.put("address",house.getAddr());
		obj.put("phone",house.getOwnerPhone());
		obj.put("description",house.getDesc());
		obj.put("expireTime",house.getExpireTime());
		JSONObject ret = new JSONObject();
		ret.put("result","OK");
		ret.put("data",obj);
		return ret;
	}

	public static JSONObject House2JSON(House house){
		JSONObject obj = new JSONObject();
		obj.put("id", house.getId());
	//	obj.put("user", User2JSON(house.getOwner()));
		obj.put("area", house.getArea());
		JSONObject price = new JSONObject();

		if(house.getDeal() == 0){
			price.put("sellPrice", house.getSellP());
		}
		else {
			price.put("rentPrice", house.getRentP());
			price.put("basePrice", house.getBaseP());
		}

		obj.put("price", price);
		obj.put("dealType", house.getDeal());
		obj.put("buildingType", house.getBuildType());
		obj.put("imageURL", house.getImageURL());

		return obj;
	}

	public static JSONObject Houses2JSON(ArrayList<House> houses){
		JSONObject obj = new JSONObject();
		obj.put("result", "ok");
		JSONArray data = new JSONArray();
		for(int i=0; i<houses.size(); i++){
			data.put(House2JSON(houses.get(i)));
		}

		obj.put("data", data);
		return obj;
	}

	public static String CompactJwt(String username){
        Key key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        String compactJwt = "";
        try {
			compactJwt = Jwts.builder()
					.setIssuer("KhaneBeDoosh")
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setId(username)
					.signWith(SignatureAlgorithm.HS256, key)
					.compact();
            return compactJwt;

        }catch (Exception e1234){
            System.out.println("dadash ?");
        }
        return null;
	}

	public static JSONObject JwtParser( String compactJwt ) throws Exception{
	    JSONObject res = new JSONObject();
	    res.put("iss",Jwts.parser().setSigningKey(new SecretKeySpec(secret.getBytes(), "HmacSHA256")).parseClaimsJws(compactJwt).getBody().getIssuer());
        res.put("iat",Jwts.parser().setSigningKey(new SecretKeySpec(secret.getBytes(), "HmacSHA256")).parseClaimsJws(compactJwt).getBody().getIssuedAt());
        res.put("jti",Jwts.parser().setSigningKey(new SecretKeySpec(secret.getBytes(), "HmacSHA256")).parseClaimsJws(compactJwt).getBody().getId());
        return res;
	}
}
