package controller.Database;

import controller.Model.GroupHasPostModel;
import controller.Model.PostHasCommentModel;

import java.sql.*;
import java.util.ArrayList;

public class GroupHasPostOperations extends Operations{

    private final Connection connection;
    public GroupHasPostOperations(Connection connection) {
        super(connection);
        this.connection = connection;
    }


    public void delete(int groupID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM GROUPHASPOST WHERE GROUPID = ?");
            ps.setInt(1, groupID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Group " + groupID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(GroupHasPostModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO GROUPHASPOST VALUES (?,?)");
            ps.setInt(1, model.getPostID());
            ps.setInt(2, model.getGroupID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public GroupHasPostModel[] getInfo() {
        ArrayList<GroupHasPostModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERGROUPHASCHAT");

            while(rs.next()) {
                GroupHasPostModel model = new GroupHasPostModel(rs.getInt("postID"),
                        rs.getInt("groupID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new GroupHasPostModel[result.size()]);
    }

    public void update(int postID, int groupID) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE GROUPHASPOST SET POSTID = ? WHERE GROUPID = ?");
            ps.setInt(1, postID);
            ps.setInt(2, groupID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Group " + groupID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
