package controller.Model;

import java.sql.Timestamp;

public class PostModel {
    private final int postID;
    private int likes;
    private final String userName;
    private final Timestamp postTime;

    public PostModel(int postID, Timestamp postTime ,int likes, String userName) {
        this.postID = postID;
        this.postTime = postTime;
        this.likes = likes;
        this.userName = userName;
    }

    public int getPostID() {
        return postID;
    }

    public int getLikes() {
        return likes;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void userLikesPost() {
        this.likes += 1;
    }
}
