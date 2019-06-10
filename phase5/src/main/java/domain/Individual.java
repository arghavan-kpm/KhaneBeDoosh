package domain;

import java.util.*;

public class Individual extends User{
	private String username, password;
	private Integer credit;
	private Integer is_admin;
	private ArrayList<String> houses = new ArrayList<String>();

	public Individual(String n,String p,String u,String ps, int a){
		super(n,p);
		this.username = u;
		this.password = ps;
		this.credit = 0;
		this.is_admin = a;
	}

	public Individual(String n,String p,String u,String ps,int credit, int a){
		super(n,p);
		this.username = u;
		this.password = ps;
		this.credit = credit;
		this.is_admin = a;
	}

	public void charge(Integer c){
		this.credit += c;
	}

	public void AddHouse(House house){
		ORMapper.InsertPaidRel(this,house);
		credit -= 1000;
		ORMapper.UpdateUserCredit(this);
	}

	public boolean searchHouse(House house){
		return ORMapper.SelectPaidRel(this,house);
	}

	public String getUsername(){	return this.username;    }
	public String getPassword(){	return this.password;	 }
	public Integer getCredit(){		return this.credit;		 }
	public Integer getIsAdmin(){	return this.is_admin;		 }


}