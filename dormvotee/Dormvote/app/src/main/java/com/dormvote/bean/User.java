package com.dormvote.bean;

public class User {
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    private String access_token;
	private String userId;

    @Override
    public String toString() {
        return "User{" +
                "access_token='" + access_token + '\'' +
                ", userId='" + userId + '\'' +
                ", userRealname='" + userRealname + '\'' +
                ", userTel='" + userTel + '\'' +
                ", userRoleInTeam='" + userRoleInTeam + '\'' +
                '}';
    }

    private String userRealname;
    private String userTel;
    private String userRoleInTeam;

    public User(String access_token, String userId, String userRealname, String userTel, String userRoleInTeam) {
        this.access_token = access_token;
        this.userId = userId;
        this.userRealname = userRealname;
        this.userTel = userTel;
        this.userRoleInTeam = userRoleInTeam;
    }



	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserRoleInTeam() {
		return userRoleInTeam;
	}

	public void setUserRoleInTeam(String userRoleInTeam) {
		this.userRoleInTeam = userRoleInTeam;
	}

}
