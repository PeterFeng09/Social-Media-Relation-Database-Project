package controller.Database;
import controller.Model.ReplyModel;
import controller.Model.TxtCommentModel;

import java.sql.*;
import java.util.ArrayList;

public class ReplyOperations extends Operations{
    private final Connection connection;
    public ReplyOperations(Connection connection) {
        super(connection);
        this.connection = connection;

    }

    public void delete(int replyID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Reply WHERE ReplyID = ?");
            ps.setInt(1, replyID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Reply " + replyID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();

        }
    }

    public void insert(ReplyModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Reply VALUES (?,?,?,?,?,?)");
            ps.setInt(1, model.getReplyID());
            ps.setTimestamp(2, model.getReplyTime());
            ps.setString(3, model.getText());
            ps.setInt(4, model.getLikes());
            ps.setString(5, model.getUserName());
            ps.setInt(6, model.getCommentID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public ReplyModel[] getInfo() {
        ArrayList<ReplyModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Reply");


            while(rs.next()) {
                ReplyModel model = new ReplyModel(rs.getInt("ReplyID"), rs.getTimestamp("ReplyTime"),
                        rs.getString("Text"), rs.getInt("Likes"), rs.getString("UserName"), rs.getInt("CommentID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ReplyModel[result.size()]);
    }

    public void update(int id,String text) {
        try {
            // TODO: Might need to add more fields for update
            PreparedStatement ps = connection.prepareStatement("UPDATE Reply SET Text = ? WHERE ReplyID = ?");
            ps.setString(1, text);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Reply " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
