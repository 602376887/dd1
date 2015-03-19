package com.dormvote.bean;

public class Picture {
	private String url;

    @Override
    public String toString() {
        return "Picture{" +
                "url='" + url + '\'' +
                ", uploadBy='" + uploadBy + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    private String uploadBy;
	private String remark;

	public Picture(String url, String uploadBy, String remark) {
		super();
		this.url = url;
		this.uploadBy = uploadBy;
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUploadBy() {
		return uploadBy;
	}

	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
