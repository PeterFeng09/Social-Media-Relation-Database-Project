package controller.Model;

import java.sql.Timestamp;

public class ReplyModel {
    private final int replyID;
    private final String userName;
    private final Timestamp replyTime;
    private String text;
    private final int commentID;
    private int likes;

    public ReplyModel(int replyID, Timestamp replyTime, String text, int likes, String userName, int commentID) {
        this.replyID = replyID;
        this.userName = userName;
        this.replyTime = replyTime;
        this.commentID = commentID;
        this.likes = likes;
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public int getReplyID() {
        return replyID;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getReplyTime() {
        return replyTime;
    }

    public String getText() {
        return text;
    }

    public int getCommentID() {
        return commentID;
    }

    public void userLikesReply() {
        this.likes += 1;
    }

    public void userRepliesComment(String text) {
        this.text = text;
    }
}
