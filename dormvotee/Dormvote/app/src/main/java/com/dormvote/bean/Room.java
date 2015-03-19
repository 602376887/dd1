package com.dormvote.bean;

import java.util.List;

public class Room {
	private String roomId;
    private String roomTitle;
    private String roomStatus;
    private String roomScore;
    private List<Picture> roomPic;
    private List<RoomItemAndScore> roomItemScore;

    public List<RoomItemAndScore> getRoomItemScore() {
        return roomItemScore;
    }

    public void setRoomItemScore(List<RoomItemAndScore> roomItemScore) {
        this.roomItemScore = roomItemScore;
    }

    public Room(String roomId, String roomTitle, String roomStatus, String roomScore, List<Picture> roomPic, List<RoomItemAndScore> roomItemScore) {
        this.roomId = roomId;
        this.roomTitle = roomTitle;
        this.roomStatus = roomStatus;
        this.roomScore = roomScore;
        this.roomPic = roomPic;
        this.roomItemScore = roomItemScore;
    }



    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomTitle='" + roomTitle + '\'' +
                ", roomStatus='" + roomStatus + '\'' +
                ", roomScore='" + roomScore + '\'' +
                ", roomPic=" + roomPic +
                ", roomItemScore=" + roomItemScore +
                '}';
    }
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomScore() {
        return roomScore;
    }

    public void setRoomScore(String roomScore) {
        this.roomScore = roomScore;
    }

    public List<Picture> getRoomPic() {
        return roomPic;
    }

    public void setRoomPic(List<Picture> roomPic) {
        this.roomPic = roomPic;
    }




}
