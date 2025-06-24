package controller.Model;

public class ChatModel {
    private final int chatID;
    private final String text;

public ChatModel(int chatID, String text) {
    this.chatID = chatID;
    this.text = text;
}

    public int getChatID() {
        return chatID;
    }

    public String getText() {
        return text;
    }
}