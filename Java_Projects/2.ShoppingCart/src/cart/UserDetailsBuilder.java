package cart;

public class UserDetailsBuilder implements UserDetailsBuilderPlan {

    private UserDetailsPlan user_details;

    public UserDetailsBuilder(){
        this.user_details = new UserDetails();
    }

    @Override
    public void buildUserAddressId(int userAddressId) {
        user_details.setUserAddressId(userAddressId);
    }

    @Override
    public void buildUserId(int userId) {
        user_details.setUserId(userId);
    }

    @Override
    public void buildUserPhoneNumber(long phoneNumber) {
        user_details.setUserPhoneNumber(phoneNumber);
    }

    @Override
    public void buildUserAddress(String userAddress) {
        user_details.setUserAddress(userAddress);
    }

    @Override
    public void buildUserPinCode(int userPinCode) {
        user_details.setUserPinCode(userPinCode);
    }

    @Override
    public UserDetailsPlan getUserDetails() {
        return this.user_details;
    }
}
