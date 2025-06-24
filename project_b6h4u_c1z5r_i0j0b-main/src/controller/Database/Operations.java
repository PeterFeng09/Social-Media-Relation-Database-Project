package controller.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class Operations {
    protected static final String WARNING_TAG = "[WARNING]";
    protected static final String EXCEPTION_TAG = "[EXCEPTION]";

    private final Connection connection;

    public Operations(Connection connection) {
        this.connection = connection;
    }
    public void delete(){

    }

    public void insert(){

    }

    public Object getInfo(){
        return null;
    }

    public void update() {

    }

    protected void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
