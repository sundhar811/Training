package com.shopping.cart;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login{
    private int id;
    private String acctype;
    private String username;
    private String password;
    
//    public Login(){
//    	
//    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAcctype() {
		return acctype;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", acctype=" + acctype + ", username=" + username + ", password=" + password + "]";
	}
}
