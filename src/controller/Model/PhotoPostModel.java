package controller.Model;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PhotoPostModel extends PostModel{

    private int postID;
    private  String userName;
    private Timestamp postTime;
    private Blob photo;
    private int likes;

    public PhotoPostModel(int postID, Timestamp postTime, int likes, String userName, Blob photo){
        super(postID, postTime, likes, userName);
        this.photo = photo;
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

    public int getLikes() {
        return likes;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void userPostPhoto(Blob photo) {
        this.photo = photo;
    }
}
