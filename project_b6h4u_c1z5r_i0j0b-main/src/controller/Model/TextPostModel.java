package controller.Model;

import java.sql.Timestamp;

public class TextPostModel extends PostModel {

    private int postID;
    private String text;
    private int likes;
    private Timestamp postTime;
    private String userName;

    public TextPostModel(int postID, Timestamp postTime, int likes, String userName, String text) {
        super(postID, postTime, likes,userName);
        this.text = text;
    }

    public int getPostID() {
        return postID;
    }

    public String getText() {
        return text;
    }

    public int getLikes() {
        return likes;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public String getUserName() {
        return userName;
    }
}
