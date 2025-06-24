package controller.Model;

import java.sql.Timestamp;

public class SavedFilesModel {

    private final int fileID;

    private final Timestamp savedTime;

    private final String userName;

    public SavedFilesModel(int fileID, Timestamp savedTime, String userName) {
        this.fileID = fileID;
        this.savedTime = savedTime;
        this.userName = userName;
    }

    public int getFileID() {
        return fileID;
    }

    public Timestamp getSavedTime() {
        return savedTime;
    }

    public String getUserName() {
        return userName;
    }
}
