package domain;

import java.util.*;

public class Individual extends User{
	private String username, password;
	private Integer credit;
	private ArrayList<String> houses = new ArrayList<String>();
	public Individual(String n,String p,String u,String ps){
		super(n,p);
		this.username = u;
		this.password = ps;
		this.credit = 0;
	}
	
	public void charge(Integer c){
		this.credit += c;
	}

	public void AddHouse(String username){

		houses.add(username);
		credit -= 1000;
	}

	public boolean searchHouse(String username){
		for(int i=0; i<houses.size(); i++){
			if(houses.get(i).equals(username))
				return true;
		}
		return false;
	}

	public String getUsername(){	return this.username;    }
	public String getPassword(){	return this.password;	 }
	public Integer getCredit(){		return this.credit;		 }


}