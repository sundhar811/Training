package com.webapp;

public interface SellerBuilderPlan {

    public void buildSellerId(int sid);

    public void buildSellerName(String sname);

    public void buildPassWord(String pwd);

    public void buildName(String name);

    public void buildEmail(String email);

    public SellerPlan getSeller();

}
