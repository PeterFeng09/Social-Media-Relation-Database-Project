package controller.Database;

import controller.Model.UserHasChatModel;

import java.sql.*;
import java.util.ArrayList;

public class UserHasChatOperations extends Operations{

    private final Connection connection;
    public UserHasChatOperations (Connection connection) {
        super(connection);
        this.connection = connection;
    }


    public void delete(int chatID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM USERHASCHAT WHERE CHATID = ?");
            ps.setInt(1, chatID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " user chat " + chatID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(UserHasChatModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USERHASCHAT VALUES (?,?)");
            ps.setInt(1, model.getChatID());
            ps.setString(2, model.getUserName());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public UserHasChatModel[] getInfo() {
        ArrayList<UserHasChatModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERHASCHAT");

            while(rs.next()) {
                UserHasChatModel model = new UserHasChatModel(rs.getInt("ChatID"),
                        rs.getString("UserName"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new UserHasChatModel[result.size()]);
    }

    public void update(int chatID, String userName) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE USERHASCHAT SET USERNAME = ? WHERE CHATID = ?");
            ps.setString(1, userName);
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

    public ResultSet join(ResultSet rs, String a, String b) {
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT " + b + " FROM USERHASCHAT c1 INNER JOIN USERHASCHAT c2 " +
                    "ON c1.chatid like c2.chatid AND c1.username <> c2.username AND " + a );

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return rs;
    }

//    public ResultSet ProjectAndSelect(ResultSet rs, String a, String b) {
//        String sql;
//
//        if(b != null) {
//            sql = "SELECT " + a + " FROM USERHASCHAT WHERE " + b;
//        } else {
//            sql = "SELECT " + a + " FROM USERHASCHAT";
//        }
//
//        try {
//            Statement stmt = connection.createStatement();
//            rs = stmt.executeQuery(sql);
//
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//
//        return rs;
//    }
}
