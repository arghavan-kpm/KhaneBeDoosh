package domain;

import java.util.*;

public class House{
	private String id;
	private int area; // metraj
	private String buildingType;
	private String address;
	private String imageURL;
	private int dealType;
	private int basePrice;
	private int rentPrice;
	private int sellPrice;
	private String phone;
	private String description;
	private String expireTime;
	private User owner;
	private ArrayList<String> userName = new ArrayList<String>();

	public House(String id, int area, String buildingType, String address, String imageURL
		,int dealType, int basePrice, int rentPrice, int sellPrice, String phone
		,String description, String expireTime, User owner){


		this.id = id;
		this.area = area;
		this.buildingType = buildingType;
		this.address = address;
		this.imageURL = imageURL;
		this.dealType = dealType;
		this.basePrice = basePrice;
		this.rentPrice = rentPrice;
		this.sellPrice = sellPrice;
		this.phone = phone;
		this.description = description;
		this.expireTime = expireTime;
		this.owner = owner;
	}



	public int getArea(){
		return area;
	}

	public String getBuildType(){
		return buildingType;
	}

	public int getDeal(){
		return dealType;
	}

	public int getSellP(){
		return sellPrice;
	}

	public int getBaseP(){
		return basePrice;
	}

	public int getRentP(){
		return basePrice;
	}

	public String getImageURL(){	return imageURL;	}

	public String getId(){	return id;	}

	public String getAddr() { return  address; }

	public String getDesc() { return description; }

	public String getOwnerPhone(){  return owner.getPhone();    }
}