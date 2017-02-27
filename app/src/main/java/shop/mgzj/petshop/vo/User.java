package shop.mgzj.petshop.vo;

import java.io.Serializable;

/**
 * Created by 星野悠 on 2017/2/23.
 */

public class User implements Serializable{
    public String userName ;
    public String password ;
    public String cookies ;
    public String email ;

    private User(){

    }

    private static User user ;

    public synchronized static User obtain(){
        if(user == null){
            user = new User() ;
        }
        return user ;
    }

    @Override
    public String toString() {
        return "User{" +
                "cookies='" + cookies + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
