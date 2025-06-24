package controller.ui.query;
import controller.Database.RegUserOperations;
import controller.Database.TextPostOperations;
import controller.Model.PostModel;
import controller.ui.LogIn;
import controller.ui.PickUserChat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class QueryMenu extends JFrame {
    private final RegUserOperations rp;
//    private final AddressTimezonePostalCodeOperations atp;
    private final TextPostOperations tpp;

    final private Connection connection;
    final private String userName;

    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width) / 2;
    int centerY = (d.height - r.height) / 2;


    public QueryMenu(Connection connection, String userName) {
        this.connection = connection;
        this.userName = userName;
        //Initialize the tables
        rp = new RegUserOperations(connection);
//        atp = new AddressTimezonePostalCodeOperations(connection);
        tpp = new TextPostOperations(connection);

//        atp.insert(new AddressTimezonePostalCodeModel("23 street rd", "PST","n4n3n2"));
//        rp.insert(new RegUserModel("bob","bob@gmail.com", 22, "23 street rd",
//                "N4N3N2", "9876543215"));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tpp.insert(new PostModel(1, timestamp, 10, "bob"));
    }

    public void showFrame() {

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        panel.setLayout(new GridLayout(12, 1, 10, 10));

        JLabel title = new JLabel("Hi, " + userName + "! Please select from the following options");
        panel.add(title);

        //Buttons
        JButton viewButton = new JButton("View Table of Other Users");
        viewButton.addActionListener(e -> {
            viewTable();
        });
        panel.add(viewButton);


//        JButton insertButton = new JButton("Insert");
//        insertButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                Insert insert= new Insert(rp,atp);
//                insert.showFrame();
//            }
//        });
//        panel.add(insertButton);


        JButton selectionButton = new JButton("Filter for information on users");
        selectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Selection selection = new Selection(rp);
                selection.showFrame();
            }
        });
        panel.add(selectionButton);



        JButton projectionButton = new JButton("View account information");
        projectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Projection projection = new Projection(rp);
                projection.showFrame();
            }
        });
        panel.add(projectionButton);


//        JButton joinButton = new JButton("Join");
//        joinButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                Join join = new Join(rp);
//                join.showFrame();
//            }
//        });
//        panel.add(joinButton);

        JButton aggregateGroupByButton = new JButton("Find all users with the same?");
        aggregateGroupByButton.addActionListener(e -> {
            AggregateGroupBy aggGroup = new AggregateGroupBy(rp);
            aggGroup.showFrame();
        });
        panel.add(aggregateGroupByButton);

        JButton aggregateHavingButton = new JButton("Find all users above a certain age ");
        aggregateHavingButton.addActionListener(e -> {
            AggregateHaving aggHaving = new AggregateHaving(rp);
            aggHaving.showFrame();
        });
        panel.add(aggregateHavingButton);


        JButton nestedAggregateButton = new JButton("Count the number of users above a certain age");
        nestedAggregateButton.addActionListener(e -> {
            NestedAggregate nestedAggregate = new NestedAggregate(rp);
            nestedAggregate.showFrame();
        });
        panel.add(nestedAggregateButton);

        JButton divideButton = new JButton("See Post History");
        divideButton.addActionListener(e -> {
            GetUserLikes gul = new GetUserLikes(connection);
            gul.showFrame();
        });
        panel.add(divideButton);


        JButton chatButton = new JButton("Chat With User");
        chatButton.addActionListener(e -> {
            PickUserChat puc = new PickUserChat(connection, userName);
//                setVisible(false);
            puc.showFrame();
        });
        panel.add(chatButton);


        JButton logOutButton = new JButton("Log Out");
        logOutButton.addActionListener(e -> {
            LogIn li = new LogIn(connection);
            setVisible(false);
            JDialog loPopup = new JDialog(this, "Succesfully Logged Out");
            JLabel lab = new JLabel("Goodbye " + userName + "!");
            lab.setAlignmentX(Component.CENTER_ALIGNMENT);
            loPopup.add(lab);
            loPopup.setSize(200, 100);
            loPopup.setLocation(centerX, centerY);
            li.showFrame();
            loPopup.setVisible(true);

        });
        panel.add(logOutButton);


        this.setContentPane(panel);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setTitle("Main Menu");

        this.pack();

        this.setLocation(centerX, centerY);
        // make the window visible
        this.setVisible(true);
    }

    public void viewTable() {

        ResultSet rs = null;
        rs = rp.projectUsers(rs, userName);

        try {
            ArrayList<ArrayList<Object>> data = new ArrayList<>();

            while (rs.next()) {
                ArrayList<Object> tempRow = new ArrayList<>();
                tempRow.add(0, rs.getString("UserName"));
                tempRow.add(1, rs.getString("Email"));
                tempRow.add(2, rs.getString("Age"));
                data.add(tempRow);
            }
            Object[][] dataList = data.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);

            JFrame f;
            f = new JFrame();
            String[] column = {"UserName", "Email", "Age"};
            JTable jt = new JTable(dataList, column);
            JScrollPane sp = new JScrollPane(jt);
            f.add(sp);
            f.setSize(900, 500);
            f.setVisible(true);
        } catch (Throwable e) {
            System.out.println("ERROR: " + e);
        }

//        RegUserModel[] models = rp.getInfo();
//        String[][] data = new String[models.length][6];
//        for(int i =0; i <models.length; i++) {
//            data[i][0] = models[i].getUserName();
//            data[i][1] = models[i].getEmail();
//            data[i][2] = String.valueOf(models[i].getAge());
//            data[i][3] = models[i].getAddress();
//            data[i][4] = models[i].getPostalCode();
//            data[i][5] = models[i].getPhoneNumber();
//        }
//        JFrame f;
//        f = new JFrame();
//        String[] column = {"UserName", "Email", "Age"};
//        JTable jt = new JTable(data, column);
//        JScrollPane sp = new JScrollPane(jt);
//        f.add(sp);
//        f.setSize(900, 500);
//        f.setVisible(true);
    }
}

