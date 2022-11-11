package org.views;

import org.controller.ClientController;

import javax.swing.*;

public class HomeView extends javax.swing.JFrame {
    private final ClientController clientController;
    JList<String> roomList;

    public HomeView(ClientController clientController) {
        this.clientController = clientController;
        roomList = this.clientController.getRoomList();
        if (roomList == null) {
            roomList = new JList<String>();
        }
        initComponents();
    }


    private void createRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.dispose();
        new CreateRoomView(clientController).setVisible(true);
    }

    private void joinRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.dispose();
        new JoinRoomView(clientController).setVisible(true);
    }

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        this.clientController.deleteToken();
        this.dispose();
        new LoginView(this.clientController).setVisible(true);
    }

    private void roomListMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            String room = roomList.getSelectedValue().split("-")[0];
            this.clientController.setRoom(room);
            this.dispose();
            new ChatView(clientController).setVisible(true);
        }
    }

    private void initComponents() {
        JButton createRoomButton = new javax.swing.JButton();
        JButton joinRoomButton = new javax.swing.JButton();
        JButton logoutButton = new javax.swing.JButton();
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Room List");
        createRoomButton.setText("Create Room");
        createRoomButton.addActionListener(this::createRoomButtonActionPerformed);
        joinRoomButton.setText("Join Room");
        joinRoomButton.addActionListener(this::joinRoomButtonActionPerformed);
        logoutButton.setText("Logout");
        logoutButton.addActionListener(this::logoutButtonActionPerformed);
        roomList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                roomListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(roomList);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(createRoomButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(joinRoomButton)
                                                .addGap(18, 18, 18)
                                                .addComponent(logoutButton)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(createRoomButton)
                                        .addComponent(joinRoomButton)
                                        .addComponent(logoutButton))
                                .addContainerGap())
        );
        pack();
        setLocationRelativeTo(null);
    }
}
