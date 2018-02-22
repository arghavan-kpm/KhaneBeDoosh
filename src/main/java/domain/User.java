package domain;

import java.util.*;

public class User {
	protected String name,phone;
	public User(String n,String p){
		this.name = n;
		this.phone = p;
	}
	public String getName(){	return this.name;	}
	public String getPhone(){	return this.phone;	}
}