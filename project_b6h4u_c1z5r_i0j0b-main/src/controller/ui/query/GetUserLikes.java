package controller.ui.query;
import controller.Database.RegUserOperations;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GetUserLikes extends JFrame {
    final private Connection connection;
    public GetUserLikes(Connection connection) {
        this.connection = connection;
    }

    public void showFrame() {
        JLabel typeQuestion = new JLabel("Who would you like to chat with?");
        RegUserOperations ruo = new RegUserOperations(connection);

        final JComboBox postType = new JComboBox();

        try {
            ResultSet rs = null;
            rs = ruo.projectAndSelect(rs, "UserName", null);
            while (rs.next()) {
                postType.addItem(rs.getString("UserName"));
            }
            rs.close();
        } catch (Throwable err) {
            JDialog errorPopup = new JDialog(this, "Error");
            JLabel lab = new JLabel("Unkown Error!");
            errorPopup.add(lab);
            errorPopup.setSize(200, 100);
            errorPopup.setVisible(true);
        }

        JButton historyButton = new JButton("View history");

        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        contentPane.setLayout(gb);
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(typeQuestion, c);
        contentPane.add(typeQuestion);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(postType, c);
        contentPane.add(postType);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(historyButton, c);
        contentPane.add(historyButton);

        // register login button with action event handler
        historyButton.addActionListener(e -> {
            String data = "";
            if (postType.getSelectedIndex() != -1) {
                System.out.println();
                data = (String)postType.getItemAt(postType.getSelectedIndex());
            }
            ResultSet rs = null;
            viewTable(ruo.dividePosts(rs, data));

            System.out.println(data);

        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);
    }

    public void viewTable(ResultSet rs) {
        try {
            ArrayList<ArrayList<Object>> data = new ArrayList<>();

            while (rs.next()) {
                ArrayList<Object> tempRow = new ArrayList<>();
                tempRow.add(0, rs.getInt("Likes"));
                tempRow.add(1, rs.getTimestamp("PostTime"));
                data.add(tempRow);
            }
            Object[][] dataList = data.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);

            JFrame f;
            f = new JFrame();
            String[] column = {"Likes", "Post Time"};
            JTable jt = new JTable(dataList, column);
            JScrollPane sp = new JScrollPane(jt);
            f.add(sp);
            f.setSize(900, 500);
            f.setVisible(true);
        } catch (Throwable e) {
            System.out.println("ERROR: " + e);
        }
    }

}
