package controller.ui.query;

import controller.Database.RegUserOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NestedAggregate extends JFrame implements ActionListener {
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width) / 2;
    int centerY = (d.height - r.height) / 2;

    private final RegUserOperations rp;

    JTextField targetAgeT;

    public NestedAggregate(RegUserOperations rp) {
        this.rp = rp;
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        panel.setLayout(new GridLayout(3,2));

        //Prompt for arg1
        JLabel title = new JLabel("Find number of users above this age:");
        panel.add(title);
        targetAgeT = new JTextField();
        panel.add(targetAgeT);

        //Submit Button
        JButton submit = new JButton("Submit");
        panel.add(submit);
        submit.addActionListener(this);

        this.add(panel);

    }

    public void showFrame() {
        System.out.println("test");
        this.setSize(400,200);
        this.setLocation(centerX, centerY);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String targetAge = targetAgeT.getText().trim();
        JFrame f;
        f=new JFrame();
        JTable jt = rp.makeTableNestedAggregate(rp.nestedAggregate(targetAge));
        JScrollPane sp=new JScrollPane(jt);
        f.add(sp);
        f.setSize(900,500);
        f.setLocation(centerX,centerY);
        f.setVisible(true);
        dispose();
    }
}