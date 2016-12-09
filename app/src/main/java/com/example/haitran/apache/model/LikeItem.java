package com.example.haitran.apache.model;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class LikeItem {
    private String imageItemLike;
    private String itemName;

    public LikeItem(String imageItemLike, String itemName) {
        this.imageItemLike = imageItemLike;
        this.itemName = itemName;
    }

    public String getImageItemLike() {
        return imageItemLike;
    }

    public String getItemName() {
        return itemName;
    }
}
