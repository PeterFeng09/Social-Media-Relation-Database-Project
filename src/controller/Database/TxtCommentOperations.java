package controller.Database;
import controller.Model.TxtCommentModel;

import java.sql.*;
import java.util.ArrayList;

public class TxtCommentOperations extends Operations{
    private final Connection connection;
    public TxtCommentOperations(Connection connection) {
        super(connection);
        this.connection = connection;

    }

    public void delete(int commentID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM TxtComment WHERE CommentID = ?");
            ps.setInt(1, commentID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " TxtComment " + commentID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();

        }
    }

    public void insert(TxtCommentModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO TxtComment VALUES (?,?,?,?,?,?)");
            ps.setInt(1, model.getCommentID());
            ps.setTimestamp(2, model.getCommentTime());
            ps.setString(3, model.getText());
            ps.setInt(4, model.getLikes());
            ps.setString(5, model.getUserName());
            ps.setInt(6, model.getPostID());


            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public TxtCommentModel[] getInfo() {
        ArrayList<TxtCommentModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Comment");


            while(rs.next()) {
                TxtCommentModel model = new TxtCommentModel(rs.getInt("ChatID"), rs.getTimestamp("CommentTime"),
                        rs.getString("Text"), rs.getInt("Likes"), rs.getInt("PostID"), rs.getString("UserName"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new TxtCommentModel[result.size()]);
    }

    public void update(int id, String text) {
        try {
            //TODO: Might need to add more fields for update
            PreparedStatement ps = connection.prepareStatement("UPDATE TxtComment SET Text = ? WHERE CommentID = ?");
            ps.setString(1, text);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Comment " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
