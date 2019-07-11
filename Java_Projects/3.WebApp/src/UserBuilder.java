package com.webapp;

public class UserBuilder implements UserBuilderPlan {

    private UserPlan user;

    public UserBuilder(){
        this.user = new User();
    }


    @Override
    public void buildUserId(int uid){
        user.setUserId(uid);
    }

    @Override
    public void buildUserName(String uname){
        user.setUserName(uname);
    }

    @Override
    public void buildPassWord(String pwd){
        user.setPassWord(pwd);
    }

    @Override
    public void buildName(String name){
        user.setName(name);
    }

    @Override
    public void buildEmail(String email){
        user.setEmail(email);
    }

    @Override
    public UserPlan getUser() {
        return this.user;
    }
}
