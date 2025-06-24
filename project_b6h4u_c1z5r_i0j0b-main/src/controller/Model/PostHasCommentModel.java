package controller.Model;

public class PostHasCommentModel {

    private final int postID;
    private final int commentID;

    public PostHasCommentModel(int postID, int commentID) {
        this.postID = postID;
        this.commentID = commentID;
    }

    public int getPostID() {
        return postID;
    }

    public int getCommentID() {
        return commentID;
    }
}
