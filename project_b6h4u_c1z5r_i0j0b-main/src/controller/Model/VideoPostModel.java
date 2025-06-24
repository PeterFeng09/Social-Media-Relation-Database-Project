package controller.Model;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class VideoPostModel extends PostModel {

    private int postID;
    private String userName;
    private Timestamp postTime;
    private Blob video;
    private int likes;

    public VideoPostModel(int postID, Timestamp postTime, int likes, String userName, Blob video){
        super(postID, postTime, likes,userName);
        this.video = video;
    }

    public int getPostID() {
        return postID;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public Blob getVideo() {
        return video;
    }

    public void userPostVideo(Blob video) {
        this.video = video;
    }
}
