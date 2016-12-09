package com.example.haitran.apache.manager;

import com.example.haitran.apache.model.HeaderItem;
import com.example.haitran.apache.model.LikeItem;

import java.util.ArrayList;

/**
 * Created by Hai Tran on 10/3/2016.
 */

public class NavigationManager {
    public static NavigationManager instance;
    private ArrayList<Object> arrayList;


    public NavigationManager(){
        arrayList=new ArrayList<>();
        arrayList.add(new HeaderItem("bg_blue"));
        arrayList.add(new LikeItem("ic_sd_card","sdcard"));
        arrayList.add(new LikeItem("ic_folder_1","DCIM"));
        arrayList.add(new LikeItem("ic_folder_1","Download"));
        arrayList.add(new LikeItem("ic_folder_1","Movies"));
        arrayList.add(new LikeItem("ic_folder_1","Music"));
        arrayList.add(new LikeItem("ic_folder_1","Pictures"));
        arrayList.add(new LikeItem("ic_photo","Images"));
        arrayList.add(new LikeItem("ic_audio","Audio"));
        arrayList.add(new LikeItem("ic_video","Videos"));
        arrayList.add(new LikeItem("ic_doc","Documents"));
        arrayList.add(new LikeItem("ic_android","Apps"));
    }
    public static NavigationManager getInstance(){
        if (instance==null){
            instance=new NavigationManager();
        }
        return instance;
    }

    public ArrayList<Object> getArrayList() {
        return arrayList;
    }

    public String getNameNavigation(int position){
        LikeItem likeItem=(LikeItem) arrayList.get(position);
        return likeItem.getItemName();
    }

}
