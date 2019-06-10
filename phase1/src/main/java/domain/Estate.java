package domain;

import java.io.StringReader;
import java.util.*;
import org.json.*;

public class Estate{
	private ArrayList<House> individuals = new ArrayList<House>();
	private ArrayList<House> realEstate = new ArrayList<House>();


	public Estate(){
	}

	public House getHouseById(String id){
		for(int i=0; i<individuals.size(); i++){
			if(individuals.get(i).getId().equals(id)){
				return individuals.get(i);
			}
		}
		return null;
	}

	public House getRealEstateHouseById(String id){
		for(int i=0; i<realEstate.size(); i++){
			if(realEstate.get(i).getId().equals(id)){
				return realEstate.get(i);
			}
		}
		return null;
	}

	public void getEstateJSON()throws Exception{
		JSONObject obj = new JSONObject(Tools.GETreq("http://acm.ut.ac.ir/khaneBeDoosh/house"));
		/*JsonReader jr;
		StringReader sr = new StringReader(Tools.GETreq("http://acm.ut.ac.ir/khaneBeDoosh/house"));
		jr = Json.createReader( sr );
		JsonObject obj = jr.readObject();
		jr.close();
*/
		System.err.println(obj);
		JSONArray arr = obj.getJSONArray("data");

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

			realEstate.add(new House(id , area, buildT, "", image ,dealT, baseP,
					rentP, sellP, "","", "", InfoBank.getUserByName("بنگاه اول")));
		}
	}

	public ArrayList<House> search(SearchForm form) throws Exception{

		getEstateJSON();

		ArrayList<House> estate = new ArrayList<House>();
		estate.addAll(individuals);
		estate.addAll(realEstate);

		ArrayList<House> searchRes = new ArrayList<House>();

		for(int i=0; i< estate.size(); i++){

		    //( estate.get(i).getSellP() <= form.getMaxP()|| estate.get(i).getRentP() <= form.getMaxP() )
			if(estate.get(i).getArea() >= form.getMinArea() ){

				if( (form.getDeal() == 0 && estate.get(i).getDeal() == 0 && estate.get(i).getSellP() <= form.getMaxP()) ||
					(form.getDeal() == 1 && estate.get(i).getDeal() == 1 && estate.get(i).getRentP() <= form.getMaxP()) ||
                        (form.getDeal() == -1 &&
                                ( (estate.get(i).getDeal() == 1 && estate.get(i).getRentP() <= form.getMaxP())
                                        || (estate.get(i).getDeal() == 0 && estate.get(i).getSellP() <= form.getMaxP()) )) ){

					if(form.getBuildType().equals("") == false && form.getBuildType().equals(estate.get(i).getBuildType()))
						searchRes.add(estate.get(i));

					else if(form.getBuildType().equals(""))
						searchRes.add(estate.get(i));
				}

			}
		}

		return searchRes;
	}

	public void addHouse(String buildingType, int area, String dealType, int price,
							String address, String phone, String description, User owner){

		int deal = 0;
		int basePrice = 0;
		int rentPrice = 0;
		int sellPrice = 0;

		if(dealType.equals("اجاره")){
			deal = 1;
			rentPrice = price;
			basePrice = 0;
			sellPrice = 0;
		}
		if(dealType.equals("خرید")){
			deal = 0;
			rentPrice = 0;
			basePrice = 0;
			sellPrice = price;
		}

		individuals.add(new House(Tools.UUI(), area, buildingType, address, "no-pic.jpg",deal, basePrice,
							 rentPrice, sellPrice, phone,description, "", owner));
	}
}