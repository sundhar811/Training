package cart;

import java.sql.Timestamp;

public class BillBuilder implements BillBuilderPlan{
    private BillPlan bill;

    public BillBuilder(){
        this.bill = new Bill();
    }

    @Override
    public void buildBillId(int billId) {
        bill.setBillId(billId);
    }

    @Override
    public void buildUserAddressId(int userAddressId) {
        bill.setUserAddressId(userAddressId);
    }

    @Override
    public void buildUserId(int userId) {
        bill.setUserId(userId);
    }

    @Override
    public void buildTotalPrice(float totalPrice) {
        bill.setTotalPrice(totalPrice);
    }

    @Override
    public void buildMOP(String mop) {
        bill.setModeOfPayment(mop);
    }

    @Override
    public void buildTimeStamp(Timestamp timestamp) {
        bill.setTimeStamp(timestamp);
    }

    @Override
    public BillPlan getBill() {
        return this.bill;
    }
}
