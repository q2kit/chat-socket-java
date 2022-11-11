package org.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.controller.ClientController;

public class AddMemberView extends javax.swing.JFrame {
    private final ClientController clientController;
    private javax.swing.JTextField usernameTextField = new javax.swing.JTextField();

    public AddMemberView(ClientController clientController) {
        this.clientController = clientController;
        initComponents();
    }

    private void addMemberButtonActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        String username = this.usernameTextField.getText();
        if (username.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Username cannot be empty");
            return;
        }
        String res = this.clientController.addMember(username);
        if (res.equals("ok")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Add member successfully");
            this.clientController.getChatView().addMember(username);
            this.dispose();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, res);
        }
    }

    private void initComponents() {
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        javax.swing.JButton addMemberButton = new javax.swing.JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Member");
        jLabel1.setText("Username");
        addMemberButton.setText("Add Member");
        addMemberButton.addActionListener(this::addMemberButtonActionPerformed);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(addMemberButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addMemberButton)
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(null);
    }
}