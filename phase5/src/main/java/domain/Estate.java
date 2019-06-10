package domain;

import java.io.StringReader;
import java.util.*;
import org.json.*;

public class Estate{


	public Estate(){
	}

	public House getHouseById(String id){
		return ORMapper.SelectHouse(id);
	}


	public ArrayList<House> search(SearchForm form) throws Exception{


		return ORMapper.SelectAllHouses(form);
	}

	public void addHouse(String buildingType, int area, String dealType, int sellPrice, int rentPrice, int basePrice,
							String address, String phone, String description, User owner){

		int deal = 0;

		if(dealType.equals("اجاره")){
			deal = 1;

		}
		if(dealType.equals("خرید")){
			deal = 0;

		}

		House house = new House(Tools.UUI(), area, buildingType, address, "no-pic.jpg",deal, basePrice,
					rentPrice, sellPrice, phone,description, "", owner);

		ORMapper.InsertHouse( house );

		ORMapper.InsertUserOwnRel((Individual) owner, house);
		ORMapper.InsertPaidRel((Individual) owner, house);



	}
}