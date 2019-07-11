package cart;

public class UserDetails implements UserDetailsPlan {
    private int userAddressId;
    private int userId;
    private long phoneNumber;
    private String userAddress;
    private int userPinCode;

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
    public void setUserPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public long getUserPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String getUserAddress() {
        return this.userAddress;
    }

    @Override
    public void setUserPinCode(int userPinCode) {
        this.userPinCode = userPinCode;
    }

    @Override
    public int getUserPinCode() {
        return this.userPinCode;
    }

    @Override
    public String toString() {
        String display_text = "Address ID: "+getUserAddressId()+"\nUser ID: "+getUserId()+
                "\nPhone Number: "+getUserPhoneNumber()+"\nAddress: "+getUserAddress()+"\n" +
                "Pin Code: "+getUserPinCode();

        return display_text;
    }
}
