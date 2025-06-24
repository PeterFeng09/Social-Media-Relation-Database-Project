package controller.ui.query;

import controller.Database.RegUserOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggregateHaving extends JFrame implements ActionListener {
    //TODO maybe have a drop down menu instead of textfield?
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width) / 2;
    int centerY = (d.height - r.height) / 2;

    private String targetAge;

    private final RegUserOperations rp;

    JTextField targetAgeT;

    public AggregateHaving(RegUserOperations rp) {
        this.rp = rp;
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        panel.setLayout(new GridLayout(3,2));

        //Prompt for arg1
        JLabel title = new JLabel("Find all the users above this age (enter an age):");
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
        this.setSize(400,200);
        this.setLocation(centerX, centerY);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        targetAge = targetAgeT .getText().trim();
        JFrame f;
        f=new JFrame();
        JTable jt = rp.makeTableAggregateHaving(rp.AggregateHaving(targetAge));
        JScrollPane sp=new JScrollPane(jt);
        f.add(sp);
        f.setSize(900,500);
        f.setLocation(centerX,centerY);
        f.setVisible(true);
        dispose();
    }
}