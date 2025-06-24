package controller.Database;

import controller.Model.VerifiedUserModel;

import java.sql.*;
import java.util.ArrayList;

public class VerifiedUserOperations extends Operations{
    private final Connection connection;

    public VerifiedUserOperations(Connection connection, Connection connection1) {
        super(connection);
        this.connection = connection;
    }

    public void delete(String userName) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Company WHERE UserName = ?");
            ps.setString(1, userName);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Company " + userName + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(VerifiedUserModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO VerifiedUser VALUES (?,?)");
            ps.setString(1, model.getUserName());
            ps.setFloat(2, model.getMonthlyFee());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public VerifiedUserModel[] getInfo() {
        ArrayList<VerifiedUserModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM VerifiedUser");

            while(rs.next()) {
                VerifiedUserModel model = new VerifiedUserModel(rs.getString("UserName"), rs.getString("Email"), rs.getInt("Age"),
                        rs.getString("Address"), rs.getString("PostalCode"), rs.getString("PhoneNumber"), rs.getFloat("MonthlyFee"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new VerifiedUserModel[result.size()]);
    }

    public void update(String userName, float monthlyFee) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE VerifiedUser SET MonthlyFee=?  WHERE UserName = ?");
            ps.setFloat(1, monthlyFee);
            ps.setString(2, userName);

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
}
