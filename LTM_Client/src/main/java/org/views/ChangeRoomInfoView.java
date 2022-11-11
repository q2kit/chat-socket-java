package org.views;

import org.controller.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangeRoomInfoView extends javax.swing.JFrame {
    private final ClientController clientController;
    private javax.swing.JTextField roomNameTextField;
    private javax.swing.JTextField roomPasswordTextField;

    public ChangeRoomInfoView(ClientController clientController) {
        this.clientController = clientController;
        initComponents();
    }

    private void backButtonActionPerformed(ActionEvent actionEvent) {
        this.dispose();
    }

    private void changeButtonActionPerformed(ActionEvent actionEvent) {
        String roomName = roomNameTextField.getText();
        String roomPassword = roomPasswordTextField.getText();
        String res = clientController.setRoomInfo(roomName, roomPassword);
        if (res.equals("ok")) {
            JOptionPane.showMessageDialog(this, "Change room info successfully");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, res);
        }
    }

    private void initComponents() {
        // btn change, back
        // show current room info, change it
        javax.swing.JButton changeButton = new javax.swing.JButton();
        javax.swing.JButton backButton = new javax.swing.JButton();
        javax.swing.JLabel roomNameLabel = new javax.swing.JLabel();
        javax.swing.JLabel roomPasswordLabel = new javax.swing.JLabel();

        roomNameTextField = new javax.swing.JTextField();
        roomPasswordTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change Room Info");
        setResizable(false);

        changeButton.setText("Change");
        changeButton.addActionListener(this::changeButtonActionPerformed);
        backButton.setText("Back");
        backButton.addActionListener(this::backButtonActionPerformed);

        roomNameLabel.setText("Room Name");
        roomPasswordLabel.setText("Room Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(changeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(roomNameLabel)
                            .addComponent(roomPasswordLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(roomPasswordTextField)
                            .addComponent(roomNameTextField))))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomNameLabel)
                    .addComponent(roomNameTextField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roomPasswordLabel)
                    .addComponent(roomPasswordTextField))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changeButton)
                    .addComponent(backButton))
                .addGap(30, 30, 30))
        );

        pack();
        setLocationRelativeTo(null);
    }
}
