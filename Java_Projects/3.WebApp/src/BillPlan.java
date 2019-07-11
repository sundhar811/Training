package com.webapp;

import java.sql.Timestamp;

public interface BillPlan {

    public void setBillId(int billId);

    public int getBillId();

    public void setUserAddressId(int userAddressId);

    public int getUserAddressId();

    public void setUserId(int userId);

    public int getUserId();

    public void setTotalPrice(float totalPrice);

    public float getTotalPrice();

    public void setModeOfPayment(String modeOfPayment);

    public String getModeOfPayment();

    public void setTimeStamp(Timestamp timeStamp);

    public Timestamp getTimeStamp();
}
