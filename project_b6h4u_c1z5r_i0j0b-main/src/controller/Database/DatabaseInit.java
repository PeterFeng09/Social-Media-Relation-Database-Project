package controller.Database;

import java.sql.*;

import controller.ui.LogIn;

public class DatabaseInit {
    // Use this version of the ORACLE_URL if you are running the code off of the server
    //	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    // Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
//    private static final String WARNING_TAG = "[WARNING]";

    private Connection connection = null;


    public DatabaseInit() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

//    public void close() {
//        try {
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }

    public void login(String username, String password) {
        try {
            if (connection != null) {
                connection.close();
            }

            connection = DriverManager.getConnection(ORACLE_URL, username, password);
            connection.setAutoCommit(false);

            System.out.println("\nConnected to Oracle!");
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

    public void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

//    public void databaseSetup() {
//            createTable("AddressTimezonePostalCode", "(Address VARCHAR(50) PRIMARY KEY, Timezone VARCHAR(10), PostalCode VARCHAR(10))");
//            createTable("RegUser", "(UserName VARCHAR(50) PRIMARY KEY, Email VARCHAR(50) UNIQUE NOT NULL, Age INTEGER, Address VARCHAR(50), PostalCode VARCHAR(6), PhoneNumber CHAR(10) UNIQUE, FOREIGN KEY (Address) REFERENCES  AddressTimezonePostalCode(Address) ON DELETE SET NULL)");
//            createTable("VerifiedUser", "(UserName VARCHAR(50) PRIMARY KEY, MonthlyFee FLOAT, FOREIGN KEY (UserName) REFERENCES RegUser(UserName)ON DELETE CASCADE)");
//            createTable("Company","(UserName VARCHAR(50) PRIMARY KEY, CompanyID VARCHAR(10) UNIQUE, FOREIGN KEY (UserName) REFERENCES RegUser(UserName) ON DELETE CASCADE)");
//            createTable("Post", "(PostID INTEGER PRIMARY KEY, PostTime TIMESTAMP,  Likes INTEGER, UserName VARCHAR(50), FOREIGN KEY (UserName) REFERENCES RegUser(UserName) ON DELETE SET NULL)");
//            createTable("TextPost", "(PostID INTEGER PRIMARY KEY, Text NVARCHAR2(280), FOREIGN KEY (PostID) REFERENCES Post(PostID) ON DELETE CASCADE)");
//            createTable("PhotoPost", "(PostID INTEGER PRIMARY KEY, Picture BLOB, FOREIGN KEY (PostID) REFERENCES Post(PostID) ON DELETE CASCADE)");
//            createTable("VideoPost", "(PostID INTEGER PRIMARY KEY, Video BLOB, FOREIGN KEY (PostID) REFERENCES  Post(PostID) ON DELETE CASCADE)");
//            createTable("TxtComment", "(CommentID INTEGER PRIMARY KEY, CommentTime TIMESTAMP, Text  NVARCHAR2(280), Likes INTEGER, UserName VARCHAR(50), PostID INTEGER, FOREIGN KEY (UserName) REFERENCES  RegUser(UserName) ON DELETE SET NULL, FOREIGN KEY (PostID) REFERENCES Post(PostID) ON DELETE SET NULL)");
//            createTable("PostHasComment", "(PostID INTEGER, CommentID INTEGER, PRIMARY KEY (PostID, CommentID), FOREIGN KEY (PostID) REFERENCES Post(PostID) ON DELETE CASCADE, FOREIGN KEY (CommentID) REFERENCES TxtComment(CommentID) ON DELETE CASCADE)");
//            createTable("Reply", "(ReplyID INTEGER, ReplyTime TIMESTAMP, Text NVARCHAR2(280), Likes INTEGER, UserName VARCHAR(50), CommentID INTEGER, PRIMARY KEY (ReplyID, CommentID), FOREIGN KEY (CommentID) REFERENCES  TxtComment(CommentID) ON DELETE CASCADE, FOREIGN KEY (UserName) REFERENCES RegUser(UserName) ON DELETE SET NULL)");
//            createTable("UserGroup", "(GroupID INTEGER PRIMARY KEY, GroupName VARCHAR(50))");
//            createTable("Chat", "(ChatID INTEGER PRIMARY KEY, Text NVARCHAR2(280))");
//            createTable("UserHasChat", "(ChatID INTEGER, UserName VARCHAR(50), PRIMARY KEY (ChatID, UserName), FOREIGN KEY (UserName) REFERENCES RegUser(UserName) ON DELETE CASCADE, FOREIGN KEY (ChatID) REFERENCES Chat(ChatID) ON DELETE CASCADE)");
//            createTable("UserGroupHasChat", "(ChatID INTEGER PRIMARY KEY, GroupID INTEGER, FOREIGN KEY (GroupID) REFERENCES UserGroup(GroupID) ON DELETE CASCADE, FOREIGN KEY (ChatID) REFERENCES Chat(ChatID) ON DELETE CASCADE)");
//            createTable("SavedFile", "(FileID INTEGER PRIMARY KEY, SavedDate TIMESTAMP, UserName VARCHAR(50), FOREIGN KEY (UserName) REFERENCES RegUser(UserName) ON DELETE CASCADE)");
//            createTable("AdCostLength", "(AdLength TIMESTAMP PRIMARY KEY, Cost NUMBER(19,4))");
//            createTable("CompanyBuysAd", "(AdID INTEGER PRIMARY KEY, UserName VARCHAR(50), StartDate TIMESTAMP NOT NULL, EndDate TIMESTAMP NOT NULL, AdLength TIMESTAMP, FOREIGN KEY (UserName) REFERENCES RegUser(UserName) ON DELETE SET NULL, FOREIGN KEY (AdLength) REFERENCES AdCostLength(AdLength) ON DELETE SET NULL)");
//            createTable("UserParticipatesInGroup", "(GroupID INTEGER, UserName VARCHAR(50), PRIMARY KEY (GroupID, UserName), FOREIGN KEY (GroupID) REFERENCES UserGroup(GroupID) ON DELETE CASCADE, FOREIGN KEY (UserName) REFERENCES RegUser(UserName) ON DELETE CASCADE)");
//            createTable("UserFollowsUser", "(FollowerUserName VARCHAR(50), FollowedUserName VARCHAR(50), PRIMARY KEY (FollowerUserName,FollowedUserName), FOREIGN KEY (FollowerUserName) REFERENCES RegUser(UserName) ON DELETE CASCADE, FOREIGN KEY (FollowedUserName) REFERENCES RegUser(UserName) ON DELETE CASCADE)");
//            createTable("GroupHasPost", "(GroupId INTEGER, PostID INTEGER, PRIMARY KEY  (GroupID,PostID), FOREIGN KEY (GroupID) REFERENCES UserGroup(GroupID) ON DELETE  CASCADE, FOREIGN KEY (PostID) REFERENCES Post(PostID) ON DELETE CASCADE)");
//    }
//
//    private void createTable(String t_name, String features) {
//        dropTableIfExists(t_name);
//
//        try {
//            Statement stmt = connection.createStatement();
//            stmt.execute("CREATE TABLE " + t_name + features);
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage() + " " + t_name);
//        }
//    }
//
//    private void dropTableIfExists(String t_name) {
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet rs = stmt.executeQuery("select table_name from user_tables");
//
//            while(rs.next()) {
//                if(rs.getString(1).equalsIgnoreCase(t_name)) {
//                    stmt.execute("DROP TABLE " + t_name + " CASCADE CONSTRAINTS");
//                    break;
//                }
//            }
//
//            rs.close();
//            stmt.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//        }
//    }
//

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        DatabaseInit di = new DatabaseInit();
        //TODO: CHANGE LOGIN AND PASSWORD TO USE
        di.login("ora_zilvi20", "a63073431");

        LogIn logIn = new LogIn(di.getConnection());
        logIn.showFrame();

        di.rollbackConnection();
    }
}
