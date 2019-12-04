// powered bu SCUDRT
import com.alibaba.fastjson.*;

public class Test{
    public static void main(String[] args){
        //create an account
        JSONObject json = new JSONObject();
        json.put("userID", "scudrt");
        json.put("password", "123456");
        System.out.println(json.toJSONString());

        boolean result = UserAccountClientManager.getInstance().signup(json);
        if (result){
            System.out.println("signup succeed");
        }else{
            System.out.println("sign up failed");
        }
        result = UserAccountClientManager.getInstance().login(json);
        if (result){
            System.out.println("login succeed");
        }else{
            System.out.println("login failed");
        }
    }
}
