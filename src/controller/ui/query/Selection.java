package controller.ui.query;

import controller.Database.RegUserOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Selection extends JFrame implements ActionListener {
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width) / 2;
    int centerY = (d.height - r.height) / 2;

    private String targetColumn;
    private String condition;

    private final RegUserOperations rp;

    JTextField attribute1;
    JTextField attribute2;

    public Selection(RegUserOperations rp) {
        this.rp = rp;
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        panel.setLayout(new GridLayout(3,2));

        //Prompt for arg1
        JLabel title = new JLabel("What would you like to filter for (for example Age)");
        panel.add(title);
        attribute1 = new JTextField();
        panel.add(attribute1);

        //Prompt for arg2
        JLabel description = new JLabel("Please select what the value should be equal to (i.e. 22 for Age");
        panel.add(description);
        attribute2 = new JTextField();
        panel.add(attribute2);

        //Button
        JButton submit = new JButton("Submit");
        panel.add(submit);
        submit.addActionListener(this);


        this.add(panel);
        this.setTitle("Please specify what attribute to select for");

    }

    public void showFrame() {
        this.setSize(800,300);
        this.setLocation(centerX, centerY);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        targetColumn = attribute1.getText().trim();
        condition= attribute2.getText().trim();
        JFrame f;
        f=new JFrame();
        JTable jt = rp.makeTable(rp.select(targetColumn ,condition));
        JScrollPane sp=new JScrollPane(jt);
        f.add(sp);
        f.setSize(900,500);
        f.setLocation(centerX,centerY);
        f.setVisible(true);
        dispose();
    }
}
