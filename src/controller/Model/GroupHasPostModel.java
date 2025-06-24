package controller.Model;

public class GroupHasPostModel {
    private final int postID;
    private final int groupID;

    public GroupHasPostModel(int postID, int groupID) {
        this.postID = postID;
        this.groupID = groupID;
    }

    public int getPostID() {
        return postID;
    }

    public int getGroupID() {
        return groupID;
    }
}
