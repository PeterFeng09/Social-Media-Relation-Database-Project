package controller.Model;

import java.sql.Timestamp;

public class TxtCommentModel {

    private final int commentID;
    private final Timestamp commentTime;
    private final String text;
    private int likes;
    private final String userName;

    private final int postID;

    public TxtCommentModel(int commentID, Timestamp commentTime,String text, int likes, int postID, String userName) {
        this.commentID = commentID;
        this.commentTime = commentTime;
        this.userName = userName;
        this.text = text;
        this.likes = likes;
        this.postID = postID;

    }

//    public void userPostComment(String text) {
//        this.text = text;
//    }

    public int getPostID() {
        return postID;
    }

    public void userLikesComment() {
        this.likes += 1;
    }

    public int getCommentID() {
        return commentID;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public String getText() {
        return text;
    }

    public int getLikes() {
        return likes;
    }

    public String getUserName() {
        return userName;
    }
}
