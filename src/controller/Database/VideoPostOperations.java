package controller.Database;
import controller.Model.PhotoPostModel;
import controller.Model.VideoPostModel;

import java.sql.*;
import java.util.ArrayList;

public class VideoPostOperations extends Operations{
    private final Connection connection;
    public VideoPostOperations(Connection connection) {
        super(connection);
        this.connection = connection;

    }

    public void delete(int postID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Post WHERE PostID = ?");
            ps.setInt(1, postID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Post " + postID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();

        }
    }

    public void insert(VideoPostModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO VideoPost VALUES (?,?,?,?,?)");
            ps.setInt(1, model.getPostID());
            ps.setTimestamp(2, model.getPostTime());
            ps.setInt(3, model.getLikes());
            ps.setString(4, model.getUserName());
            ps.setBlob(5, model.getVideo());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public VideoPostModel[] getInfo() {
        ArrayList<VideoPostModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Reply");


            while(rs.next()) {
                VideoPostModel model = new VideoPostModel(rs.getInt("PostID"), rs.getTimestamp("PostTime"),
                        rs.getInt("Likes"), rs.getString("UserName"), rs.getBlob("Video"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new VideoPostModel[result.size()]);
    }

    public void update(int id,Blob video) {
        try {
            // TODO: Might need to add more fields for update
            PreparedStatement ps = connection.prepareStatement("UPDATE Video SET Blob = ? WHERE PostID = ?");
            ps.setBlob(1, video);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Post " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
