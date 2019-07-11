package cart;

import java.io.IOException;

public interface UserBuilderPlan {

    public void buildUserId(int uid);

    public void buildUserName(String uname);

    public void buildPassWord(String pwd);

    public void buildName(String name);

    public void buildEmail(String email);

    public UserPlan getUser();

}
