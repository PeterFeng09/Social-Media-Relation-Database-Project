package controller.Database;
import controller.Model.AddressTimezonePostalCodeModel;

import java.sql.*;
import java.util.ArrayList;
public class AddressTimezonePostalCodeOperations extends Operations{
    private final Connection connection;

    public AddressTimezonePostalCodeOperations (Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public void delete(String address) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM AddressTimezonePostalCode WHERE Address = ?");
            ps.setString(1, address);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " AddressTimezonePostalCode with address " + address + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(AddressTimezonePostalCodeModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ADDRESSTIMEZONEPOSTALCODE VALUES (?,?,?)");
            ps.setString(1, model.getAddress());
            ps.setString(2, model.getTimezone());
            ps.setString(3, model.getPostalCode());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public AddressTimezonePostalCodeModel[] getInfo() {
        ArrayList<AddressTimezonePostalCodeModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM addressTimezonePostalCode");

            while(rs.next()) {
                AddressTimezonePostalCodeModel model = new AddressTimezonePostalCodeModel(rs.getString("Address"), rs.getString("Timezone"), rs.getString("PostalCode"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new AddressTimezonePostalCodeModel[result.size()]);
    }

    public void update(String timezone, String postalCode, String address) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE AddressTimezonePostalCode SET Timezone = ?, PostalCode= ?, WHERE Address = ?");
            ps.setString(1, timezone);
            ps.setString(2, postalCode);
            ps.setString(3, address);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " AddressTimezonePostalCode with address " + address + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

}
