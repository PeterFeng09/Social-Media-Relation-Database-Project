package controller.ui.query;

import controller.Database.RegUserOperations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggregateGroupBy extends JFrame implements ActionListener {
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width) / 2;
    int centerY = (d.height - r.height) / 2;

    private String targetColumn;

    private final RegUserOperations rp;

    JTextField targetColumnT;

    public AggregateGroupBy(RegUserOperations rp) {
        this.rp = rp;
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,20,30,20));
        panel.setLayout(new GridLayout(3,2));

        //Prompt for arg1
        JLabel title = new JLabel("Count the number of users that has the same (for example age, postal code): ");
        panel.add(title);
        targetColumnT = new JTextField();
        panel.add(targetColumnT);

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
        targetColumn = targetColumnT .getText().trim();
        JFrame f;
        f=new JFrame();
        JTable jt = rp.makeTableAggregateGroupBy(rp.aggregateGroupBy(targetColumn),targetColumn);
        JScrollPane sp=new JScrollPane(jt);
        f.add(sp);
        f.setSize(900,500);
        f.setLocation(centerX,centerY);
        f.setVisible(true);
        dispose();
    }
}