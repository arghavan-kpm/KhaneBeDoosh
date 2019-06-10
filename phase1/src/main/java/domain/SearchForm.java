package domain;

import java.util.*;

public class SearchForm{
	private int minArea;
	private String buildingType;
	private int deal;
	private int maxPrice;

	public SearchForm(String minArea, String buildingType, String deal, String maxPrice){
		if(minArea.equals(""))
			this.minArea = 0;
		else
			this.minArea = Tools.decode(minArea);


		if(maxPrice.equals(""))
			this.maxPrice = 2000000000;
		else
			this.maxPrice = Tools.decode(maxPrice);


		if(deal.equals("اجاره"))
			this.deal = 1;
		else if(deal.equals("خرید"))
			this.deal = 0;
		else
			this.deal = -1;


		this.buildingType = buildingType;

	}

	public String getBuildType(){
		return buildingType;
	}

	public int getDeal(){
		return deal;
	}

	public int getMinArea(){
		return minArea;
	}

	public int getMaxP(){
		return maxPrice;
	}
}