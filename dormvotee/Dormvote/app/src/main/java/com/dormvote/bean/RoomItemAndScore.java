package com.dormvote.bean;

/**
 * Created by Administrator on 2015/1/9.
 */
public class RoomItemAndScore {
    private String itemId;
    private String itemScore;


    public RoomItemAndScore(String itemId, String itemScore) {
        this.itemId = itemId;
        this.itemScore = itemScore;
    }


    @Override
    public String toString() {
        return "RoomItemAndScore{" +
                "itemId='" + itemId + '\'' +
                ", itemScore='" + itemScore + '\'' +
                '}';
    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemScore() {
        return itemScore;
    }

    public void setItemScore(String itemScore) {
        this.itemScore = itemScore;
    }


}
