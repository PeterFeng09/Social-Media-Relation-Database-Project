package controller.ui;

import controller.Database.AddressTimezonePostalCodeOperations;
import controller.Database.RegUserOperations;
import controller.Model.AddressTimezonePostalCodeModel;
import controller.Model.RegUserModel;
import controller.ui.query.QueryMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;

public class LogIn extends JFrame{
    final private Connection connection;
    public LogIn(Connection connection) {
        this.connection = connection;
    }

    public void showFrame() {

        JButton newUserButton = new JButton("New User");
        JButton existingUserButton = new JButton("Existing User");

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
        gb.setConstraints(newUserButton, c);
        contentPane.add(newUserButton);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(existingUserButton, c);
        contentPane.add(existingUserButton);


        newUserButton.addActionListener(e -> {
            MakeUser mu = new MakeUser(connection);
            mu.showFrame();
            setVisible(false);
        });

        existingUserButton.addActionListener(e -> {
            ChooseUser cu = new ChooseUser(connection);
            cu.showFrame();
            setVisible(false);
        });


        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
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
}

class MakeUser extends JFrame{
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width)/2;
    int centerY = (d.height - r.height)/2;
    final private Connection connection;

    public MakeUser(Connection connection) {
        this.connection = connection;
    }

    public void showFrame() {
        JTextField nameF = new JTextField("Enter user name", 10);
        JTextField mailF = new JTextField("Enter e-mail", 10);
        JTextField ageF = new JTextField("Enter age", 10);
        JTextField numberF = new JTextField("Enter phone #", 10);
        JTextField postalF = new JTextField("Enter postal code", 10);
        JTextField addressF = new JTextField("Enter address", 10);
        JTextField timezoneF = new JTextField("Enter timezone", 10);

        JButton signUpButton = new JButton("Sign Up");

        nameF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nameF.setText("");
            }
        });
        mailF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mailF.setText("");
            }
        });
        ageF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ageF.setText("");
            }
        });
        numberF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                numberF.setText("");
            }
        });
        postalF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                postalF.setText("");
            }
        });
        addressF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addressF.setText("");
            }
        });
        timezoneF.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                timezoneF.setText("");
            }
        });

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
        gb.setConstraints(nameF, c);
        contentPane.add(nameF);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(mailF, c);
        contentPane.add(mailF);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(ageF, c);
        contentPane.add(ageF);

        // place the username label
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 5, 0);
        gb.setConstraints(numberF, c);
        contentPane.add(numberF);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(addressF, c);
        contentPane.add(addressF);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(postalF, c);
        contentPane.add(postalF);

        // place the text field for the username
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(10, 0, 5, 10);
        gb.setConstraints(timezoneF, c);
        contentPane.add(timezoneF);

        // place the signup button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(signUpButton, c);
        contentPane.add(signUpButton);

        // register signup button with action event handler
        signUpButton.addActionListener(e -> {
            AddressTimezonePostalCodeOperations atpco = new AddressTimezonePostalCodeOperations(connection);
            RegUserOperations ruo = new RegUserOperations(connection);

            String uName = nameF.getText().trim();
            String eMail = mailF.getText().trim();
            int age = Integer.parseInt(ageF.getText().trim());
            String pNumber = numberF.getText().trim();
            String postalC = postalF.getText().trim();
            String address = addressF.getText().trim();
            String tZone = timezoneF.getText().trim();

            try {
                //TODO: CHECK IF ADDRESS ALREADY EXISTS
                atpco.insert(new AddressTimezonePostalCodeModel(address, tZone, postalC));
                ruo.insert(new RegUserModel(uName, eMail, age, address, postalC, pNumber));

                QueryMenu menu = new QueryMenu(connection, uName);
                setVisible(false);
                menu.showFrame();
            } catch (Error err) {
                JDialog errorPopup = new JDialog(this, "Error");
                JLabel lab = new JLabel("One or more fields are invalid!");
                errorPopup.add(lab);
                errorPopup.setSize(200, 100);
                errorPopup.setLocation(centerX, centerY);
                errorPopup.setVisible(true);
            }

        });

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        this.setLocation( centerX, centerY);

        // make the window visible
        this.setVisible(true);
    }
}

class ChooseUser extends JFrame{
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width)/2;
    int centerY = (d.height - r.height)/2;
    final private Connection connection;
    String userName = "";
    public ChooseUser(Connection connection) {
        this.connection = connection;
    }

    public void showFrame() {
        JLabel typeQuestion = new JLabel("What is your user name?");
        JTextField userNameF = new JTextField(10);

        JButton signInButton = new JButton("Sign In");

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
        gb.setConstraints(userNameF, c);
        contentPane.add(userNameF);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(signInButton, c);
        contentPane.add(signInButton);

        // register login button with action event handler
        signInButton.addActionListener(e -> {
            RegUserOperations ruo = new RegUserOperations(connection);
            ResultSet rs = null;

            try {
                rs = ruo.projectAndSelect(rs, "USERNAME", "USERNAME LIKE '" + userNameF.getText().trim() + "'");
                while (rs.next()) {
                    userName = rs.getString("UserName");
                }
                System.out.println(userName);
                if (userName.isBlank()) {
                    throw new Exception();
                }

                rs.close();

                QueryMenu menu = new QueryMenu(connection, userName);
                setVisible(false);
                menu.showFrame();
//                PickUserChat puc = new PickUserChat(connection, userName);
//                setVisible(false);
//                puc.showFrame();
            } catch (Throwable er) {
                JDialog errorPopup = new JDialog(this, "Error");
                JLabel lab = new JLabel("No user under this name");
                errorPopup.add(lab);
                errorPopup.setSize(200, 100);
                errorPopup.setLocation(centerX, centerY);
                errorPopup.setVisible(true);
            }
        });

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        this.setLocation(centerX, centerY);

        // make the window visible
        this.setVisible(true);
    }
}
