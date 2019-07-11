package cart;

import java.sql.Timestamp;

public class Bill implements BillPlan {
    private int billId;
    private int userAddressId;
    private int userId;
    private float totalPrice;
    private String modeOfPayment;
    private Timestamp timestamp;

    @Override
    public void setBillId(int billId) {
        this.billId = billId;
    }

    @Override
    public int getBillId() {
        return this.billId;
    }

    @Override
    public void setUserAddressId(int userAddressId) {
        this.userAddressId = userAddressId;
    }

    @Override
    public int getUserAddressId() {
        return this.userAddressId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public float getTotalPrice() {
        return this.totalPrice;
    }

    @Override
    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    @Override
    public String getModeOfPayment() {
        return this.modeOfPayment;
    }

    @Override
    public void setTimeStamp(Timestamp timeStamp) {
        this.timestamp = timeStamp;
    }

    @Override
    public Timestamp getTimeStamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        String display_text = "Bill ID: "+getBillId()+"\nUser ID: "+getUserId()+
                "\nUser Add. Id: "+getUserAddressId()+"\nTotal Price: "+getTotalPrice()+
                "\nMode Of Payment: "+getModeOfPayment()+"\nTime: "+getTimeStamp();

        return display_text;
    }
}
