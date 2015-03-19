package com.dormvote.bean;

public class StatusAndExplain {
	private  int status;
    private  String explain;

	public StatusAndExplain(int status, String explain) {
		super();
		this.status = status;
		this.explain = explain;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}
