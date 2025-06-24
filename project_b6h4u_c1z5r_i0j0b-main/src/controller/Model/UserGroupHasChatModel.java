package controller.Model;

public class UserGroupHasChatModel {
    private final int chatID;
    private final int groupID;

    public UserGroupHasChatModel(int chatID, int groupID) {
        this.chatID = chatID;
        this.groupID = groupID;
    }

    public int getChatID() {
        return chatID;
    }

    public int getGroupID() {
        return groupID;
    }
}
