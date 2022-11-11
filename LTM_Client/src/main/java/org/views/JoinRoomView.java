package org.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.controller.ClientController;

public class JoinRoomView extends javax.swing.JFrame {
    private final ClientController clientController;

    public JoinRoomView(ClientController clientController) {
        this.clientController = clientController;
        initComponents();
    }

    private JTextField roomIDTextField;
    private JTextField roomPasswordTextField;

    private void initComponents() {
        JLabel roomIDLabel = new JLabel();
        JLabel roomPasswordLabel = new JLabel();
        JButton joinRoomButton = new JButton();
        JButton backButton = new JButton();
        roomIDTextField = new JTextField();
        roomPasswordTextField = new JTextField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Join Room");
        setResizable(false);
        roomIDLabel.setText("Room ID");
        roomPasswordLabel.setText("Room Password");
        joinRoomButton.setText("Join Room");
        joinRoomButton.addActionListener(this::joinRoomButtonActionPerformed);
        backButton.setText("Back");
        backButton.addActionListener(this::backButtonActionPerformed);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(joinRoomButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(backButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(roomIDLabel).addComponent(roomPasswordLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(roomPasswordTextField).addComponent(roomIDTextField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(roomIDLabel).addComponent(roomIDTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(roomPasswordLabel).addComponent(roomPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(joinRoomButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(backButton).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pack();
        setLocationRelativeTo(null);
    }

    private void backButtonActionPerformed(ActionEvent evt) {
        this.dispose();
        new HomeView(this.clientController).setVisible(true);
    }

    private void joinRoomButtonActionPerformed(ActionEvent evt) {
        String roomID = roomIDTextField.getText();
        String roomPassword = roomPasswordTextField.getText();
        if (roomID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in the room ID field");
        } else {
            String res = this.clientController.joinRoom(roomID, roomPassword);
            if (res.equals("ok")) {
                this.dispose();
                new HomeView(this.clientController).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, res);
            }
        }
    }
}