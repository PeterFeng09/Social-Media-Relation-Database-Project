package controller.Database;
import controller.Model.SavedFilesModel;

import java.sql.*;
import java.util.ArrayList;
public class SavedFileOperations extends Operations{

    private final Connection connection;

    public SavedFileOperations(Connection connection) {
        super(connection);
        this.connection = connection;
    }
    public void delete(int fileID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM SavedFile WHERE FileAd = ?");
            ps.setInt(1, fileID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " SavedFile " + fileID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(SavedFilesModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO SavedFile VALUES (?,?,?)");
            ps.setInt(1, model.getFileID());
            ps.setTimestamp(2, model.getSavedTime());
            ps.setString(3, model.getUserName());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public SavedFilesModel[] getInfo() {
        ArrayList<SavedFilesModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SavedFile");

            while(rs.next()) {
                SavedFilesModel model = new SavedFilesModel(rs.getInt("AdID"),rs.getTimestamp("SavedDate"), rs.getString("UserName"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new SavedFilesModel[result.size()]);
    }

    public void update(int fileID, Timestamp savedDate, String userName) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE SavedFile SET SavedDate = ?, UserName = ? WHERE AdID = ?");
            ps.setTimestamp(1, savedDate);
            ps.setString(2, userName);
            ps.setInt(3, fileID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " SavedFile " + fileID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
