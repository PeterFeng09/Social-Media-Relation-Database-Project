package controller.ui.query;

import controller.Database.AddressTimezonePostalCodeOperations;
import controller.Database.RegUserOperations;
import controller.Model.AddressTimezonePostalCodeModel;
import controller.Model.RegUserModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Insert extends JFrame implements ActionListener {
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width) / 2;
    int centerY = (d.height - r.height) / 2;


    private final RegUserOperations rp;
    private final AddressTimezonePostalCodeOperations atp;

    //TextFields
    JTextField nameF;
    JTextField mailF;
    JTextField ageF;
    JTextField numberF;
    JTextField postalF;
    JTextField addressF;
    JTextField timezoneF;
    public Insert(RegUserOperations rp, AddressTimezonePostalCodeOperations atp) {
        this.rp = rp;
        this.atp = atp;
    }


    public void showFrame(){

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        panel.setLayout(new GridLayout(8,1));

        nameF = new JTextField("Enter user name", 10);
        panel.add(nameF);
        mailF = new JTextField("Enter e-mail", 10);
        panel.add(mailF);
        ageF = new JTextField("Enter age", 10);
        panel.add(ageF );
        numberF = new JTextField("Enter phone number", 10);
        panel.add(numberF);
        postalF = new JTextField("Enter postal code", 10);
        panel.add(postalF);
        addressF = new JTextField("Enter address", 10);
        panel.add(addressF);
        timezoneF = new JTextField("Enter timezone", 10);
        panel.add(timezoneF);

        JButton signUpButton = new JButton("Sign Up");
        panel.add(signUpButton);
        signUpButton.addActionListener(this);
        this.add(panel);


        this.setSize(400,700);
        this.setLocation(centerX, centerY);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String uName = nameF.getText().trim();
        String eMail = mailF.getText().trim();
        int age = Integer.parseInt(ageF.getText().trim());
        String pNumber = numberF.getText().trim();
        String postalC = postalF.getText().trim();
        String address = addressF.getText().trim();
        String tZone = timezoneF.getText().trim();
        try {
            atp.insert(new AddressTimezonePostalCodeModel(address, tZone, postalC));
            rp.insert(new RegUserModel(uName, eMail, age, address, postalC, pNumber));
        } catch (Error err) {
            JDialog errorPopup = new JDialog(this, "Error");
            JLabel lab = new JLabel("One or more fields are invalid!");
            errorPopup.add(lab);
            errorPopup.setSize(200, 100);
            errorPopup.setLocation(centerX, centerY);
            errorPopup.setVisible(true);
        }
        dispose();
    }
}
