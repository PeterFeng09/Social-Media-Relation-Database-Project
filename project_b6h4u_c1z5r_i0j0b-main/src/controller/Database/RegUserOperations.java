package controller.Database;
import controller.Model.RegUserModel;

import javax.swing.JTable;
import java.sql.*;
import java.util.ArrayList;

public class RegUserOperations extends Operations{
    private final Connection connection;
    public RegUserOperations (Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public void delete(String userName) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM RegUser WHERE UserName = ?");
            ps.setString(1, userName);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " RegUser " + userName + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(RegUserModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO RegUser VALUES (?,?,?,?,?,?)");
            ps.setString(1, model.getUserName());
            ps.setString(2, model.getEmail());
            ps.setInt(3, model.getAge());
            ps.setString(4, model.getAddress());
            ps.setString(5,model.getPostalCode());
            ps.setString(6, model.getPhoneNumber());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public RegUserModel[] getInfo() {
        ArrayList<RegUserModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RegUser");

            while(rs.next()) {
                RegUserModel model = new RegUserModel(rs.getString("UserName"), rs.getString("Email"), rs.getInt("Age"),
                rs.getString("Address"), rs.getString("PostalCode"), rs.getString("PhoneNumber"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new RegUserModel[result.size()]);
    }

    //Update
    public void update(String userName, String email, int age, String address, String postalCode, String phoneNumber) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE RegUser SET Email = ?, Age =?, Address = ?, PostalCode = ?, PhoneNumber = ? WHERE UserName = ?");
            ps.setString(1, email);
            ps.setInt(2, age);
            ps.setString(3, address);
            ps.setString(4, postalCode);
            ps.setString(5,phoneNumber);
            ps.setString(6, userName);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " User " + userName + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    //Selection
    public String[][] select(String attribute, String condition) {
        StringBuilder query = new StringBuilder("SELECT * FROM RegUser WHERE ");
        if(attribute.equals("age")) {
            query.append(attribute).append(" = ").append(condition);
        } else {
            query.append(attribute).append(" = '").append(condition).append("'");
        }

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());

            int i=0;
            String[][] result = new String[20][6];
            while(rs.next()) {
                for(int j = 0; j< 6; j++) {
                    result[i][j] = rs.getString(j+1);
                }
                i++;
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

    //Projection
    public String[][] project(ArrayList<String> columns) {
        String[][] result = new String[20][columns.size()];
        StringBuilder query = new StringBuilder("SELECT ");
        //The number of columns that will appear in ResultSet
        for(int i=0; i<columns.size()-1; i++) {
            query.append(columns.get(i)).append(", ");
        }
        query.append(columns.get(columns.size()-1));
        query.append(" FROM RegUser");
        System.out.println(query +"\n");
        System.out.println("Columns length is: " +columns.size()+"\n");
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());
            int i=0;
            while(rs.next()) {
                for(int j = 1; j <= columns.size(); j++) {
                    result[i][j-1] = rs.getString(j);
                }
                i++;
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

    //Join
    public String[][] join(String targetTable, String joinCondition, String targetColumn, String whereCondition) {
        StringBuilder query = new StringBuilder("SELECT * FROM RegUser JOIN ");
        query.append(targetTable).append(" ON RegUser.UserName = ").append(targetTable).append(".")
                .append(joinCondition).append(" WHERE ");

        if(targetColumn.equals("age")) {
            query.append(targetColumn).append(" = ").append(whereCondition);
        } else {
            query.append(targetColumn).append(" = '").append(whereCondition).append("'");
        }
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());

            int i=0;
            String[][] result = new String[20][9];
            while(rs.next()) {
                for(int j = 0; j< 6; j++) {
                    result[i][j] = rs.getString(j+1);
                }
                result[i][6] = String.valueOf(rs.getInt(7));
                result[i][7] = rs.getTimestamp(8).toString();
                result[i][8] = String.valueOf(rs.getInt(9));
                i++;
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

    public String[][] aggregateGroupBy(String targetColumn) {
        StringBuilder query = new StringBuilder("SELECT COUNT(UserName), ");
        query.append(targetColumn).append(" FROM RegUser GROUP BY ").append(targetColumn);

        return executeAggregateQuery(query);
    }


    public String[][] AggregateHaving(String targetAge) {
        StringBuilder query = new StringBuilder("SELECT UserName, Age FROM RegUser GROUP BY Username, Age HAVING Age > ");
        query.append(targetAge);

        System.out.println(query);

        return executeAggregateQuery(query);
    }

    private String[][] executeAggregateQuery(StringBuilder query) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());

            int i=0;
            String[][] result = new String[20][2];
            while(rs.next()) {
                result[i][0] = rs.getString(1);
                result[i][1] = rs.getString(2);
                i++;
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

    public JTable makeTable(String[][] result) {
        String[] column ={"UserName", "Email", "Age", "Address", "PostalCode","PhoneNumber"};
        return new JTable(result,column);
    }

    public JTable makeTableProjection(String[][] result, ArrayList<String> inputColumn) {
        String[] column = new String[inputColumn.size()];
        for(int i =0; i < inputColumn.size(); i++) {
            column[i] = inputColumn.get(i);
        }
        return new JTable(result,column);
    }

    public JTable makeTableJoin(String[][] result) {
        String[] column ={"UserName", "Email", "Age", "Address", "PostalCode","PhoneNumber","PostID","PostTime","Likes"};
        return new JTable(result,column);
    }



    public JTable makeTableAggregateGroupBy(String[][] result, String targetColumn) {
        String[] column = new String[2];
        column[0] = "Number_Of_Users";
        column[1] = targetColumn;
        return new JTable(result,column);
    }


    public JTable makeTableAggregateHaving(String[][] result) {
        String[] column = {"UserName", "Age"};
        return new JTable(result,column);
    }

    public ResultSet projectAndSelect(ResultSet rs, String a, String b) {
        String sql;

        if(b != null) {
            sql = "SELECT " + a + " FROM RegUser WHERE " + b;
        } else {
            sql = "SELECT " + a + " FROM RegUser";
        }

        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return rs;
    }

    public ResultSet projectUsers(ResultSet rs, String uName) {
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT USERNAME, EMAIL, AGE FROM REGUSER WHERE USERNAME <> '" + uName + "'" );

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return rs;
    }

    public ResultSet dividePosts(ResultSet rs, String uName) {
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT LIKES, POSTTIME FROM POST p1 " +
                    "WHERE NOT EXISTS((SELECT USERNAME FROM REGUSER r) MINUS " +
                    "(SELECT USERNAME FROM POST p2 WHERE p1.USERNAME = '"+ uName +"'))");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return rs;
    }

    public String[][] nestedAggregate(String targetAge) {
        StringBuilder query = new StringBuilder("SELECT COUNT(UserName) FROM RegUser WHERE Age >= " +
                "(SELECT MIN(Age) FROM RegUser WHERE Age > ");
        query.append(targetAge).append(")");
        System.out.println(query);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());

            int i=0;
            String[][] result = new String[1][1];
            while(rs.next()) {
                result[0][0] = rs.getString(1);
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

    public JTable makeTableNestedAggregate(String[][] result) {
        String[] column = {"Number Of User above this minimum age"};
        return new JTable(result,column);
    }
}
