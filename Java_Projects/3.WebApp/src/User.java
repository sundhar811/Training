package com.webapp;

public class User implements UserPlan {
    private int userId;
    private String userName;
    private String passWord;
    private String name;
    private String email;

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getUserId(){return this.userId;}

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName(){return this.userName;}

    @Override
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String getPassWord(){return this.passWord;}

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName(){return this.name;}

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getEmail(){return this.email;}

    @Override
    public String toString() {
        String display_text = "ID: "+getUserId()+"\nUsername: "+getUserName()+
                "\nPassword: "+getPassWord()+"\nName: "+getName()+"\nEmail: "+getEmail();

        return display_text;
    }
}
