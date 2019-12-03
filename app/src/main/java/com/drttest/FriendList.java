//powered by SCUDRT
import java.util.*;

// Works in client environment
public class FriendList{
    FriendList(){
        this.userID = "";
        this.friendIDList = new ArrayList<String>();
    }

    /** PUBLIC */
    public void setUser(String _userID){
        this.userID = _userID;
    }

    public void addFriend(String friendID){
        // TODO: check if the friend already exists
        this.friendIDList.add(friendID);
    }

    /** PRIVATE */
    private String userID;

    private ArrayList<String> friendIDList;
}
