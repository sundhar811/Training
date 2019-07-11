package com.webapp;

import java.sql.Timestamp;

public interface BillBuilderPlan {
    public void buildBillId(int billId);

    public void buildUserAddressId(int userAddressId);

    public void buildUserId(int userId);

    public void buildTotalPrice(float totalPrice);

    public void buildMOP(String mop);

    public void buildTimeStamp(Timestamp timestamp);

    public BillPlan getBill();
}
