package com.dormvote.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/1/8.
 */
public class PublicData {
    private String dataType;
    private String dataName;
    private List<PublicDataItem> itemList;

    @Override
    public String toString() {
        return "PublicData{" +
                "dataType='" + dataType + '\'' +
                ", dataName='" + dataName + '\'' +
                ", itemList=" + itemList +
                '}';
    }

    public PublicData(String dataType, String dataName, List<PublicDataItem> itemList) {
        this.dataType = dataType;
        this.dataName = dataName;
        this.itemList = itemList;
    }
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public List<PublicDataItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PublicDataItem> itemList) {
        this.itemList = itemList;
    }


}
