package cart;

public class Seller implements SellerPlan {
    private int sellerId;
    private String sellerName;
    private String passWord;
    private String name;
    private String email;

    @Override
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public int getSellerId() {
        return this.sellerId;
    }

    @Override
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public String getSellerName() {
        return this.sellerName;
    }

    @Override
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String getPassWord() {
        return this.passWord;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        String display_text = "ID: "+getSellerId()+"\nUsername: "+getSellerName()+
                "\nPassword: "+getPassWord()+"\nName: "+getName()+"\nEmail: "+getEmail();

        return display_text;
    }
}
