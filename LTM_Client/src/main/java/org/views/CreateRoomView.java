package org.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.controller.ClientController;

public class CreateRoomView extends javax.swing.JFrame {
    public CreateRoomView(ClientController clientController) {
        this.clientController = clientController;
        initComponents();
    }

    private JTextField roomNameTextField;
    private JTextField roomPasswordTextField;
    private final ClientController clientController;

    private void initComponents() {
        JLabel roomNameLabel = new JLabel();
        JLabel roomPasswordLabel = new JLabel();
        JButton createRoomButton = new JButton();
        JButton backButton = new JButton();
        roomNameTextField = new JTextField();
        roomPasswordTextField = new JTextField();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Create Room");
        setResizable(false);
        roomNameLabel.setText("Room Name");
        roomPasswordLabel.setText("Room Password");
        createRoomButton.setText("Create Room");
        createRoomButton.addActionListener(this::createRoomButtonActionPerformed);
        backButton.setText("Back");
        backButton.addActionListener(this::backButtonActionPerformed);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(createRoomButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(backButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(roomNameLabel).addComponent(roomPasswordLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(roomPasswordTextField).addComponent(roomNameTextField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(roomNameLabel).addComponent(roomNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(roomPasswordLabel).addComponent(roomPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(createRoomButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(backButton).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        pack();
        setLocationRelativeTo(null);
    }

    private void createRoomButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String roomName = roomNameTextField.getText();
        String roomPassword = roomPasswordTextField.getText();
        String id = clientController.createRoom(roomName, roomPassword);
        JOptionPane.showMessageDialog(this, "Create room successfully! Room ID: " + id);
        this.dispose();
        new HomeView(clientController).setVisible(true);
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
        new HomeView(clientController).setVisible(true);
    }
}