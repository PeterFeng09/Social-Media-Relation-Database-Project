package controller.ui.query;

import controller.Database.RegUserOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Join extends JFrame implements ActionListener{
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width) / 2;
    int centerY = (d.height - r.height) / 2;

    private final RegUserOperations rp;

    JTextField targetTableF;
    JTextField joinConditionF;
    JTextField targetColumnF;
    JTextField whereConditionF;


    public Join(RegUserOperations rp) {
        this.rp = rp;

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        panel.setLayout(new GridLayout(5,2));

        //Labels and text fields
        JLabel label1 = new JLabel("Table to join with");
        panel.add(label1);
        targetTableF = new JTextField();
        panel.add(targetTableF);

        JLabel label2  = new JLabel("Attribute to join on");
        panel.add(label2);
        joinConditionF = new JTextField();
        panel.add(joinConditionF);


        JLabel label3  = new JLabel("Attribute to filter for");
        panel.add(label3);
        targetColumnF = new JTextField();
        panel.add(targetColumnF);

        JLabel label4 = new JLabel("Attribute = ");
        panel.add(label4);
        whereConditionF = new JTextField();
        panel.add(whereConditionF);

        //Button
        JButton submit = new JButton("Submit");
        panel.add(submit);
        submit.addActionListener(this);

        this.add(panel);
        this.setTitle("Please specify which table to join and what attribute to filter for");

    }

    public void showFrame() {
        this.setSize(400,200);
        this.setLocation(centerX, centerY);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String targetTable = targetTableF.getText().trim();
        String joinCondition = joinConditionF.getText().trim();
        String targetColumn = targetColumnF.getText().trim();
        String whereCondition = whereConditionF.getText().trim();

        JFrame f;
        f=new JFrame();
        JTable jt = rp.makeTableJoin(rp.join(targetTable, joinCondition, targetColumn, whereCondition));
        JScrollPane sp=new JScrollPane(jt);

        f.add(sp);
        f.setSize(900,500);
        f.setLocation(centerX,centerY);
        f.setVisible(true);
        dispose();
    }
}
