package controller.Database;
import controller.Model.ChatModel;

import java.sql.*;
import java.util.ArrayList;

public class ChatOperations extends Operations{
    private final Connection connection;
    public ChatOperations (Connection connection) {
        super(connection);
        this.connection = connection;

    }

    public void delete(int chatID) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Chat WHERE ChatID = ?");
            ps.setInt(1, chatID);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Chat " + chatID + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void insert(ChatModel model) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Chat VALUES (?,?)");
            ps.setInt(1, model.getChatID());
            ps.setString(2, model.getText());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public ChatModel[] getInfo() {
        ArrayList<ChatModel> result = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Chat");

            while(rs.next()) {
                ChatModel model = new ChatModel(rs.getInt("ChatID"),
                        rs.getString("Text"));
                result.add(model);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }

        return result.toArray(new ChatModel[result.size()]);
    }

    public void update(int id, String text) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE chat SET Text = ? WHERE ChatID = ?");
            ps.setString(1, text);
            ps.setInt(2, id);

            int rowCount = ps.executeUpdate();
            if (rowCount == 0) {
                System.out.println(WARNING_TAG + " Chat " + id + " does not exist!");
            }

            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }
}
