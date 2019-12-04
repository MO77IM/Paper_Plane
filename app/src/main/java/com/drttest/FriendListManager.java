//powered by SCUDRT
package com.drttest;
import com.drttest.FriendList;

import java.util.*;

public class FriendListManager{
    private FriendListManager(){
        ;
    }
    /** PUBLIC */
    public static FriendListManager getInstance(){
        return instance;
    }

    /** PRIVATE */
    private static FriendListManager instance = new FriendListManager();
    private ArrayList<FriendList> friendLists;
}
