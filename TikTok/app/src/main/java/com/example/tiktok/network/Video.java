package com.example.tiktok.network;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("student_id") public String studentId;
    @SerializedName("user_name") public String userName;
    @SerializedName("image_url") public String imageUrl;
    @SerializedName("video_url") public String videoUrl;
    @SerializedName("image_w") public int imageWidth;
    @SerializedName("image_h") public int imageHeight;

    public boolean Like;

    public Video(String studentId, String userName, String imageUrl, String videoUrl) {
        this.studentId = studentId;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.Like = false;
    }

    public boolean getLike() {return Like;}
    public void setLike(boolean like) {Like = like;}

    public String getStudentId() {return studentId;}
    public String getUserName() {return userName;}
    public String getImageUrl() {return imageUrl;}
    public String getVideoUrl() {return videoUrl;}
    public int getImageWidth() {return imageWidth;}
    public int getImageHeight() {return imageHeight;}

    public void setStudentId(String studentId) {this.studentId = studentId;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setImageUrl(String imageUrl) {this.imageUrl = imageUrl;}
    public void setVideoUrl(String videoUrl) {this.videoUrl = videoUrl;}
}
