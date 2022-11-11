package org.views;

import org.controller.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class ChatView extends javax.swing.JFrame {
    private final ClientController clientController;
    private int page;
    private final int pageSize = 20;
    private ArrayList<String> messages = new ArrayList<>();
    private final javax.swing.JTextField messageTextField = new javax.swing.JTextField();
    private javax.swing.JTextArea chatTextArea;
    private ArrayList<String> members = new ArrayList<>();

    public ChatView(ClientController clientController) {
        this.page = 0; // start from page 0
        this.clientController = clientController;
        this.messages = this.clientController.getMessages(page, pageSize);
        this.members = this.clientController.getMembers();
        initComponents();
        this.chatTextArea.setText(String.join("\n", this.messages));
        this.clientController.setChatView(this);
    }

    private void addMemberButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        new AddMemberView(clientController).setVisible(true);
    }

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String message = this.messageTextField.getText();
        if (!message.isEmpty()) {
            String res = this.clientController.sendMessage(message);
            System.out.println(res);
            if (!res.equals("ok")) {
                javax.swing.JOptionPane.showMessageDialog(this, res);
                return;
            }
            this.messageTextField.setText("");
        }
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.clientController.deleteToken();
        this.dispose();
        new LoginView(this.clientController).setVisible(true);
    }

    private void leaveRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        new HomeView(this.clientController).setVisible(true);
    }

    public void appendMessage(String message) {
        this.chatTextArea.append(message);
    }

    private void listMemberButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        new ListMemberView(clientController).setVisible(true);
    }

    private void changeRoomInfoActionPerformed(ActionEvent actionEvent) {
        new ChangeRoomInfoView(clientController).setVisible(true);
    }

    public void addMember(String username) {
        if (this.members.contains(username)) {
            return;
        }
        this.members.add(username);
    }

    public ArrayList<String> getMembers() {
        return this.members;
    }

    public void removeMember(String member) {
        this.members.remove(member);
    }


    private void initComponents() {
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        javax.swing.JButton sendButton = new javax.swing.JButton();
        javax.swing.JLabel roomLabel = new javax.swing.JLabel();
        javax.swing.JLabel userLabel = new javax.swing.JLabel();
        javax.swing.JButton logoutButton = new javax.swing.JButton();
        javax.swing.JButton leaveRoomButton = new javax.swing.JButton();
        javax.swing.JButton addMemberButton = new javax.swing.JButton();
        javax.swing.JButton listMemberButton = new javax.swing.JButton();
        javax.swing.JButton changeRoomInfo = new javax.swing.JButton();
        javax.swing.JFileChooser jFileChooser = new javax.swing.JFileChooser();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat");
        setResizable(false);
        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        jScrollPane1.setViewportView(chatTextArea);
        addMemberButton.setText("Add Member");
        addMemberButton.addActionListener(this::addMemberButtonActionPerformed);
        listMemberButton.setText("List Member");
        listMemberButton.addActionListener(this::listMemberButtonActionPerformed);
        sendButton.setText("Send");
        sendButton.addActionListener(this::sendButtonActionPerformed);
        roomLabel.setText("Room: " + this.clientController.getRoom());
        userLabel.setText("User: " + this.clientController.getUsername());
        logoutButton.setText("Logout");
        logoutButton.addActionListener(this::logoutButtonActionPerformed);
        leaveRoomButton.setText("Leave Room");
        leaveRoomButton.addActionListener(this::leaveRoomButtonActionPerformed);
        changeRoomInfo.setText("Change Room Info");
        changeRoomInfo.addActionListener(this::changeRoomInfoActionPerformed);
        jScrollPane1.setViewportView(chatTextArea);
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
//            if (e.getValue() == 0) {
//                int oldHeight = chatTextArea.getSize().height;
//                System.out.println(oldHeight);
//                System.out.println("scroll to top");
//                page++;
//                ArrayList<String> newMessages = this.clientController.getMessages(page, pageSize);
//                this.messages.addAll(0, newMessages);
//                if (newMessages.size() > 0) {
//                    this.messages.addAll(0, newMessages);
//                    this.chatTextArea.setText(String.join("\n", this.messages));
//                }
//                this.chatTextArea.setCaretPosition(this.chatTextArea.getSize().height - oldHeight);
//            }
        });
        // create a button choose file, post to api, get link, send link to server
        javax.swing.JButton chooseFileButton = new javax.swing.JButton();
        chooseFileButton.setText("Choose File");
        chooseFileButton.addActionListener(e -> {
            int returnVal = jFileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooser.getSelectedFile();
                String link = this.clientController.uploadFile(file);
                if (link != null) {
                    if (link.contains("http")) {
                        link = link.substring(1, link.length() - 1);// remove " in link
                        this.clientController.sendMessage(link);
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(this, link);
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Something went wrong");
                }
            }
        });
        // create layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(roomLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(userLabel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(messageTextField)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(sendButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(addMemberButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(listMemberButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(changeRoomInfo)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(leaveRoomButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(logoutButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(chooseFileButton)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(roomLabel)
                                        .addComponent(userLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sendButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addMemberButton)
                                        .addComponent(listMemberButton)
                                        .addComponent(changeRoomInfo)
                                        .addComponent(logoutButton)
                                        .addComponent(leaveRoomButton))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chooseFileButton)
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(null);
    }
}