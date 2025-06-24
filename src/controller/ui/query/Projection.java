package controller.ui.query;

import controller.Database.RegUserOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Projection extends JFrame implements ActionListener {
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width) / 2;
    int centerY = (d.height - r.height) / 2;

    private ArrayList<String> columns;

    private final RegUserOperations rp;

    JTextField attribute1;
    JTextField attribute2;

    public Projection(RegUserOperations rp) {
        columns = new ArrayList<>();
        this.rp = rp;
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        panel.setLayout(new GridLayout(7,1,5,5));

        //Prompt for arg1
        JButton userNameButton = new JButton("userName");
        userNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                columns.add("UserName");
            }
        });
        panel.add(userNameButton);

        JButton emailButton = new JButton("email");
        emailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                columns.add("Email");
            }
        });
        panel.add(emailButton);

        JButton ageButton = new JButton("age");
        ageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                columns.add("Age");
            }
        });
        panel.add(ageButton);

        JButton addressButton = new JButton("address");
        addressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                columns.add("Address");
            }
        });
        panel.add(addressButton);

        JButton phoneNumberButton = new JButton("phoneNumber");
        phoneNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                columns.add("UserName");
            }
        });
        panel.add(phoneNumberButton);

        JButton postalCodeButton = new JButton("postalCode");
        postalCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                columns.add("postalCode");
            }
        });
        panel.add(postalCodeButton);

        JButton submit = new JButton("Submit");
        submit.addActionListener(this);
        panel.add(submit);

        this.add(panel);
        this.setTitle("Please specify what columns to return");

    }

    public void showFrame() {
        this.setSize(400,600);
        this.setLocation(centerX, centerY);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame f;
        f=new JFrame();
        JTable jt = rp.makeTableProjection(rp.project(columns),columns);
        JScrollPane sp=new JScrollPane(jt);
        f.add(sp);
        f.setSize(900,500);
        f.setLocation(centerX,centerY);
        f.setVisible(true);
        dispose();
    }
}
