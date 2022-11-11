package org.views;

import javax.swing.*;
import java.awt.event.ActionListener;

import org.controller.ClientController;


public class LoginView extends javax.swing.JFrame {
    private final ClientController clientController;

    public LoginView(ClientController clientController) {
        this.clientController = clientController;
        initComponents();
    }

    private JTextField usernameTextField;
    private JPasswordField passwordTextField;

    private void initComponents() {
        //    username label, username textfield, password label, password textfield, login button, register button
        JLabel usernameLabel = new JLabel();
        usernameTextField = new JTextField();
        JLabel passwordLabel = new JLabel();
        passwordTextField = new JPasswordField();
        JButton loginButton = new JButton();
        JButton registerButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setResizable(false);

        usernameLabel.setText("Username:");

        passwordLabel.setText("Password:");

        loginButton.setText("Login");
        loginButton.addActionListener(this::loginButtonActionPerformed);

        registerButton.setText("Register");
        registerButton.addActionListener(this::registerButtonActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameLabel).addComponent(passwordLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(usernameTextField).addComponent(passwordTextField))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(loginButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(registerButton))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(usernameLabel).addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passwordLabel).addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(loginButton).addComponent(registerButton)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String username = usernameTextField.getText();
        String password = new String(passwordTextField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            return;
        }
        String res = this.clientController.login(username, password);
        String status = res.split("/")[0];
        String message = res.split("/")[1];
        if (status.equals("success")) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.dispose();
            new HomeView(this.clientController).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, message);
        }
    }

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String username = usernameTextField.getText();
        String password = String.valueOf(passwordTextField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            return;
        }
        String res = this.clientController.register(username, password);
        String status = res.split("/")[0];
        String message = res.split("/")[1];
        if (status.equals("success")) {
            JOptionPane.showMessageDialog(this, message);
            this.dispose();
            new HomeView(this.clientController).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, message);
        }
    }
}