package controller.Database;
import controller.Model.PostModel;

import java.sql.*;
import java.util.ArrayList;

public class TextPostOperations extends Operations{
    private final Connection connection;
    public TextPostOperations(Connection connection) {
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

    public void insert(PostModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Post VALUES (?,?,?,?)");
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

    public void update(int id,String text) {
        try {
            // TODO: Might need to add more fields for update
            PreparedStatement ps = connection.prepareStatement("UPDATE TextPost SET Text = ? WHERE PostID = ?");
            ps.setString(1, text);
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
