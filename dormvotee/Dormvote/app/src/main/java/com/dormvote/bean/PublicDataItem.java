package com.dormvote.bean;

public class PublicDataItem {
	private String itemId;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    private String itemName;
	private String itemContent;
	private String itemValue;

    @Override
    public String toString() {
        return "PublicDataItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemContent='" + itemContent + '\'' +
                ", itemValue='" + itemValue + '\'' +
                '}';
    }

    public PublicDataItem(String itemId, String itemName, String itemContent,
			String itemValue) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemContent = itemContent;
		this.itemValue = itemValue;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}



	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

}
