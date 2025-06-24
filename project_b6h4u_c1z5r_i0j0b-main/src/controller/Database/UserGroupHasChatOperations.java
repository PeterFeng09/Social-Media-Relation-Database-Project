package controller.Database;

import controller.Model.UserGroupHasChatModel;

import java.sql.*;
import java.util.ArrayList;

public class UserGroupHasChatOperations extends Operations{

    private final Connection connection;
    public UserGroupHasChatOperations (Connection connection) {
        super(connection);
        this.connection = connection;
    }


    public void delete(int chatID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM USERGROUPHASCHAT WHERE CHATID = ?");
            ps.setInt(1, chatID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " user group has chat " + chatID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(UserGroupHasChatModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USERGROUPHASCHAT VALUES (?,?)");
            ps.setInt(1, model.getChatID());
            ps.setInt(2, model.getGroupID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public UserGroupHasChatModel[] getInfo() {
        ArrayList<UserGroupHasChatModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERGROUPHASCHAT");

            while(rs.next()) {
                UserGroupHasChatModel model = new UserGroupHasChatModel(rs.getInt("ChatID"),
                        rs.getInt("GroupID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new UserGroupHasChatModel[result.size()]);
    }

    public void update(int chatID, int groupID) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE USERGROUPHASCHAT SET GROUPID = ? WHERE CHATID = ?");
            ps.setInt(1, groupID);
            ps.setInt(2, chatID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " chat " + chatID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
