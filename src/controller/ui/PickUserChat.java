package controller.ui;

import javax.swing.*;
import java.awt.*;

import controller.Database.ChatOperations;
import controller.Database.UserHasChatOperations;
import controller.Model.ChatModel;
import controller.Model.UserHasChatModel;
import controller.ui.query.QueryMenu;

import java.sql.Connection;
import java.sql.ResultSet;


public class PickUserChat extends JFrame {
    Dimension d = this.getToolkit().getScreenSize();
    Rectangle r = this.getBounds();
    int centerX = (d.width - r.width)/2;
    int centerY = (d.height - r.height)/2;
    final private Connection connection;
    final private String curUserName;
    public PickUserChat(Connection connection, String curUserName) {
        this.connection = connection;
        this.curUserName = curUserName;
    }

    public void showFrame() {
        UserHasChatOperations uhco = new UserHasChatOperations(connection);
        ChatOperations co = new ChatOperations(connection);
        JLabel typeQuestion = new JLabel("Who would you like to chat with?");

        final JComboBox postType = new JComboBox();

        try {
            ResultSet rs = null;
            rs = uhco.join(rs, "c1.USERNAME like'" + curUserName + "'", "c2.USERNAME");

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

        JButton uploadButton = new JButton("Start chat");
        JButton deleteButton = new JButton("Delete chat");

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
        gb.setConstraints(uploadButton, c);
        contentPane.add(uploadButton);

        // place the login button
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        gb.setConstraints(deleteButton, c);
        contentPane.add(deleteButton);

        // register login button with action event handler
        uploadButton.addActionListener(e -> {
            String data = "";
            int chatID = -1;
            if (postType.getSelectedIndex() != -1) {
                System.out.println();
                data = (String)postType.getItemAt(postType.getSelectedIndex());
                UserHasChatOperations uco = new UserHasChatOperations(connection);
                for (UserHasChatModel user: uco.getInfo()) {
                    if(user.getUserName().equalsIgnoreCase(data)) {
                        chatID = user.getChatID();
                        break;
                    }
                }

                MakeUserChat muc = new MakeUserChat(connection, data, chatID, curUserName);
                muc.showFrame();
                setVisible(false);
            }
            System.out.println(data);

        });

        deleteButton.addActionListener(e -> {
            String otherUserName = (String)postType.getItemAt(postType.getSelectedIndex());
            int confirm = JOptionPane.showConfirmDialog(this,"Are you sure you would like to delete your chat with " +
                     otherUserName + "?");

            if (confirm == 0) {
                try {
                    int chatIDs = -1;
                    ResultSet rs = null;
                    rs = uhco.join(rs, "c1.USERNAME like'" + curUserName + "' AND c2.USERNAME like '"
                            + otherUserName + "'", "c1.ChatID");

                    if (rs.next()) {
                        System.out.println(rs.getInt("ChatID"));
                        chatIDs = rs.getInt("ChatID");
                    }
                    rs.close();

                    
                    co.delete(chatIDs);

                    QueryMenu qm = new QueryMenu(connection, curUserName);
                    setVisible(false);
                    JDialog loPopup = new JDialog(this, "Succesfully deleted!");
                    JLabel lab = new JLabel("Successfully deleted chat with " + otherUserName + "!");
                    lab.setAlignmentX(Component.CENTER_ALIGNMENT);
                    loPopup.add(lab);
                    loPopup.setSize(300, 100);
                    loPopup.setLocation(centerX, centerY);
                    qm.showFrame();
                    loPopup.setVisible(true);
                } catch (Throwable err) {
                    System.out.println("Error: " + err);
                }
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


class MakeUserChat extends JFrame {
    final private String chatName;
    final private Connection connection;
    final private int chatID;
    final private String curUserName;
    public MakeUserChat(Connection connection, String chatName, int chatID, String curUserName) {
        this.chatName = chatName;
        this.connection = connection;
        this.chatID = chatID;
        this.curUserName = curUserName;
    }

    public void showFrame() {
        JLabel chatLabel = new JLabel("Chatting with " + chatName);
        JTextField sendChat = new JTextField(10);

        JButton sendButton = new JButton("Send");
        JTextArea chatTxt = new JTextArea(15, 35);
        chatTxt.setEditable(false);
        chatTxt.setText(txtToString());
        chatTxt.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(chatTxt, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setSize(20, 20);

        chatLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel contentPane = new JPanel();
        this.setContentPane(contentPane);

        BoxLayout bl = new BoxLayout(contentPane, BoxLayout.Y_AXIS);

        contentPane.setLayout(bl);

        contentPane.setSize(100, 100);
        contentPane.setAlignmentX(CENTER_ALIGNMENT);
        contentPane.add(chatLabel);
        contentPane.add(scroll);
        contentPane.add(sendChat);
        contentPane.add(sendButton);

        sendButton.addActionListener(e -> {
            String data;
            String newChat;
            data = sendChat.getText();
            ChatOperations co = new ChatOperations(connection);

            newChat = txtToString();
            if (newChat == null) {
                newChat = "\n" + curUserName + ": " + data.trim();
            } else {
                newChat = newChat.concat("\n" + curUserName + ": " + data.trim());
            }
            co.update(chatID, newChat);
            chatTxt.setText(txtToString());

            System.out.println(data);
        });

        // anonymous inner class for closing the window
//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });

        // size the window to obtain a best fit for the components
        this.pack();

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);
    }

    private String txtToString() {
        String currentString = "";

        ChatOperations co = new ChatOperations(connection);
        ChatModel[] currentChats = co.getInfo();

        for (ChatModel chat : currentChats) {
            if (chat.getChatID() == chatID) {
                currentString = chat.getText();
                break;
            }
        }

        return currentString;
    }
}
