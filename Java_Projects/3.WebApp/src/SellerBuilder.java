package com.webapp;

public class SellerBuilder implements SellerBuilderPlan {

    private SellerPlan seller;

    public SellerBuilder(){
        this.seller = new Seller();
    }

    @Override
    public void buildSellerId(int sid) {
        this.seller.setSellerId(sid);
    }

    @Override
    public void buildSellerName(String sname) {
        this.seller.setSellerName(sname);
    }

    @Override
    public void buildPassWord(String pwd) {
        this.seller.setPassWord(pwd);
    }

    @Override
    public void buildName(String name) {
        this.seller.setName(name);
    }

    @Override
    public void buildEmail(String email) {
        this.seller.setEmail(email);
    }

    @Override
    public SellerPlan getSeller() {
        return this.seller;
    }
}
