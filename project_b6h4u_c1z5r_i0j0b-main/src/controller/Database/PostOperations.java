package controller.Database;

import controller.Model.PostModel;

import java.sql.*;
import java.util.ArrayList;

public class PostOperations extends Operations{

    private final Connection connection;
    public PostOperations (Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public void delete(int postId) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM POST WHERE POSTID = ?");
            ps.setInt(1, postId);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " RegUser " + postId + " does not exist!");
            }
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(PostModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO POST VALUES (?,?,?,?)");
            ps.setInt(1, model.getPostID());
            ps.setTimestamp(2, model.getPostTime());
            ps.setInt(3, model.getLikes());
            ps.setString(4, model.getUserName());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public PostModel[] getInfo() {
        ArrayList<PostModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM POST");

            while(rs.next()) {
                PostModel model = new PostModel(rs.getInt("PostID"), rs.getTimestamp("PostTime"),
                        rs.getInt("Likes"), rs.getString("UserName"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new PostModel[result.size()]);
    }
}
