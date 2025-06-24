package controller.Model;

public class UserGroupModel {
    private final int groupID;
    private final String groupName;

    public UserGroupModel(int groupID, String groupName) {
        this.groupID = groupID;
        this.groupName = groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }
}
