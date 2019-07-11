package com.webapp;

public interface UserDetailsBuilderPlan {

    public void buildUserAddressId(int userAddressId);

    public void buildUserId(int userId);

    public void buildUserPhoneNumber(long phoneNumber);

    public void buildUserAddress(String userAddress);

    public void buildUserPinCode(int userPinCode);

    public UserDetailsPlan getUserDetails();
}
