package controller.Database;

import controller.Model.UserGroupModel;

import java.sql.*;
import java.util.ArrayList;

public class UserGroupOperations extends Operations{

    private final Connection connection;
    public UserGroupOperations (Connection connection) {
        super(connection);
        this.connection = connection;
    }


    public void delete(int groupID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM USERGROUP WHERE GROUPID = ?");
            ps.setInt(1, groupID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " user group " + groupID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(UserGroupModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO USERGROUP VALUES (?,?)");
            ps.setInt(1, model.getGroupID());
            ps.setString(2, model.getGroupName());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public UserGroupModel[] getInfo() {
        ArrayList<UserGroupModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERGROUP");

            while(rs.next()) {
                UserGroupModel model = new UserGroupModel(rs.getInt("GroupID"),
                        rs.getString("GroupName"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new UserGroupModel[result.size()]);
    }

    public void update(int id, String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE USERGROUP SET GROUPNAME = ? WHERE GROUPID = ?");
            ps.setString(1, name);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " user group " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public String[][] selectionQuery(int groupID, String groupName) {
        String[][] result;
        StringBuilder query = new StringBuilder("SELECT * FROM UserGroup WHERE ");
        PreparedStatement ps;
        if(groupID >0 && groupName != null) {
            query.append("GroupID = " + groupID + " AND GroupName ='" + groupName + "'");
        } else if (groupID > 0) {
            query.append("GroupID = " + groupID);
        } else {
            query.append("GroupName = '" + groupName + "'");
        }

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());
            result = new String[rs.getFetchSize()][2];
            for(int i = 0; i < rs.getFetchSize(); i++) {
                result[i][0] = String.valueOf(rs.getInt(1));
                result[i][1] = rs.getString(2);
            }
            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return null;
    }

    public String[][] projectionQuery(Boolean groupID, Boolean groupName) {
        //String matrix that will hold information of the resultSet
        String[][] result;
        StringBuilder query = new StringBuilder("SELECT ");
        //The number of columns that will appear in ResultSet
        int numberOfColumns;
        PreparedStatement ps;
        if(groupID && groupName) {
            query.append("GroupID, GroupName ");
            numberOfColumns = 2;
        } else if (groupID) {
            query.append("GroupID ");
            numberOfColumns = 1;
        } else {
            query.append("GroupName ");
            numberOfColumns= 1;
        }
        query.append("FROM UserGroup");

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());
            result = new String[rs.getFetchSize()][numberOfColumns];
            for(int i = 0; i < rs.getFetchSize(); i++) {
                if(groupID && groupName) {
                    result[i][0] = String.valueOf(rs.getInt(0));
                    result[i][1] = rs.getString(1);
                } else if (groupID) {
                    result[i][0] = String.valueOf(rs.getInt(0));
                } else {
                    result[i][0] = rs.getString(1);
                }
            }
            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return null;
    }

    public String[][] joinQuery(String targetTable, String targetAttribute, String whereTable, String whereAttribute, String selectionCondition) {
        String[][] result;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM UserGroup JOIN ? ON UserGroup.GroupID = ?.GroupID WHERE ?.? = ?");
            ps.setString(1, targetTable);
            ps.setString(2, targetTable);
            ps.setString(3,whereTable);
            ps.setString(4,whereAttribute);
            ps.setString(5,selectionCondition);

            ResultSet rs = ps.executeQuery();
            result = new String[rs.getFetchSize()][3];
            for(int i = 0; i < rs.getFetchSize(); i++) {
                result[i][0] = String.valueOf(rs.getInt(1));
                result[i][1] = rs.getString(2);
                result[i][2] = rs.getString(3);
            }
            rs.close();
            ps.close();

            return result;
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return null;
    }

    public void AggregateroupQuery(String statement) {
        //TODO
    }

    public void AggregateHavingQuery(String statement) {
        //TODO
    }

    public void NestedAggregateQuery(String statement) {
        //TODO
    }

    public void division() {
        //TODO
    }

}
