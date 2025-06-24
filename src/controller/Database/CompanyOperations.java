package controller.Database;

import controller.Model.CompanyModel;

import java.sql.*;
import java.util.ArrayList;

public class CompanyOperations extends Operations{

    private final Connection connection;

    public CompanyOperations(Connection connection, Connection connection1) {
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

    public void insert(CompanyModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Company VALUES (?,?)");
            ps.setString(1, model.getUserName());
            ps.setString(2, model.getCompanyID());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public CompanyModel[] getInfo() {
        ArrayList<CompanyModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Company");

            while(rs.next()) {
                CompanyModel model = new CompanyModel(rs.getString("UserName"), rs.getString("Email"), rs.getInt("Age"),
                        rs.getString("Address"), rs.getString("PostalCode"), rs.getString("PhoneNumber"), rs.getString("CompanyID"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new CompanyModel[result.size()]);
    }

    public void update(String companyID, String userName) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Company SET CompanyID = ? WHERE UserName = ?");
            ps.setString(1, companyID);
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
