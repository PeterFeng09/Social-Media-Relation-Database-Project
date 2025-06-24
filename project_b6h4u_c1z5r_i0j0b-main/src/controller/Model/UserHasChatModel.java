package controller.Model;

public class UserHasChatModel {
    private final int chatID;
    private final String userName;

    public UserHasChatModel(int chatID, String userName) {
        this.chatID = chatID;
        this.userName = userName;
    }

    public int getChatID() {
        return chatID;
    }

    public String getUserName() {
        return userName;
    }
}
