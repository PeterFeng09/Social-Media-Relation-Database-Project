package controller.Database;

import controller.Model.PostHasCommentModel;

import java.sql.*;
import java.util.ArrayList;

public class PostHasCommentOperations extends Operations{

    private final Connection connection;
    public PostHasCommentOperations(Connection connection) {
        super(connection);
        this.connection = connection;
    }


    public void delete(int postID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM USERGROUPHASCHAT WHERE POSTID = ?");
            ps.setInt(1, postID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " post " + postID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(PostHasCommentModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO POSTHASCOMMENT VALUES (?,?)");
            ps.setInt(1, model.getPostID());
            ps.setInt(2, model.getCommentID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public PostHasCommentModel[] getInfo() {
        ArrayList<PostHasCommentModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERGROUPHASCHAT");

            while(rs.next()) {
                PostHasCommentModel model = new PostHasCommentModel(rs.getInt("postID"),
                        rs.getInt("CommentID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new PostHasCommentModel[result.size()]);
    }

    public void update(int postID, int commentID) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE USERGROUPHASCHAT SET COMMENTID = ? WHERE POSTID = ?");
            ps.setInt(1, postID);
            ps.setInt(2, commentID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " post " + postID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
