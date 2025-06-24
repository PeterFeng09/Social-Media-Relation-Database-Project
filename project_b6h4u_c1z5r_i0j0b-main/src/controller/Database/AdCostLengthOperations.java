package controller.Database;
import controller.Model.AdCostLengthModel;

import java.sql.*;
import java.util.ArrayList;
public class AdCostLengthOperations extends Operations{
    private final Connection connection;
    public AdCostLengthOperations(Connection connection) {
        super(connection);
        this.connection=connection;
    }

    public void delete(Timestamp adLength) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM AdCostLength WHERE AdLength = ?");
            ps.setTimestamp(1, adLength);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " AdCostLength with length " + adLength + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(AdCostLengthModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CompanyBuysAd VALUES (?,?)");
            ps.setTimestamp(1,model.getAdLength());
            ps.setFloat(2, model.getCost());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    //Gets all info from the table
    public AdCostLengthModel[] getInfo() {
        ArrayList<AdCostLengthModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AdCostLength");

            while(rs.next()) {
                AdCostLengthModel model = new AdCostLengthModel(rs.getTimestamp("AdLength"), rs.getFloat("Cost"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new AdCostLengthModel[result.size()]);
    }

    public void update(Timestamp adLength, float cost) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE AdCostLength SET Cost = ? WHERE AdLength = ?");
            ps.setFloat(1, cost);
            ps.setTimestamp(2, adLength);


            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " AdCostLength with length " + adLength + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }


}
