package controller.Database;
import controller.Model.CompanyBuysAdModel;

import java.sql.*;
import java.util.ArrayList;
public class CompanyBuysAdOperations extends Operations{
    private final Connection connection;

    public CompanyBuysAdOperations(Connection connection) {
        super(connection);
        this.connection = connection;
    }
    public void delete(int adID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM ComapnyBuysAd WHERE AdID = ?");
            ps.setInt(1, adID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Advertisement with adID " + adID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(CompanyBuysAdModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CompanyBuysAd VALUES (?,?,?,?,?)");
            ps.setInt(1, model.getAdID());
            ps.setString(2, model.getUserName());
            ps.setTimestamp(3, model.getStartDate());
            ps.setTimestamp(4, model.getEndDate());
            ps.setTimestamp(5,model.getAdLength());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public CompanyBuysAdModel[] getInfo() {
        ArrayList<CompanyBuysAdModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CompanyBuysAd");

            while(rs.next()) {
                CompanyBuysAdModel model = new CompanyBuysAdModel(rs.getInt("AdID"), rs.getString("UserName"), rs.getTimestamp("StartDate"),
                        rs.getTimestamp("EndDate"),rs.getTimestamp("AdLength"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new CompanyBuysAdModel[result.size()]);
    }

    public void update(int adID, String username, Timestamp startDate, Timestamp endDate, Timestamp adLength) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE CompanyBuysAd SET Username=?, StartDate = ?, EndDate = ?, AdLength = ? WHERE AdID = ?");
            ps.setString(1,username);
            ps.setTimestamp(2, startDate);
            ps.setTimestamp(3, endDate);
            ps.setTimestamp(4, adLength);
            ps.setInt(5, adID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Advertisement with adID " + adID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
