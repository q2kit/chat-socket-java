package org.views;

import org.controller.ClientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ListMemberView extends javax.swing.JFrame {
    private final ClientController clientController;
    private javax.swing.JList<String> memberList;


    public ListMemberView(ClientController clientController) {
        initComponents();
        this.clientController = clientController;
        this.memberList.setListData(clientController.getChatView().getMembers().toArray(new String[0]));
    }

    private void backButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void banButtonActionPerformed(ActionEvent evt) {
        String member = memberList.getSelectedValue();
        if (member != null) {
            String res = this.clientController.banMember(member);
            if (res.equals("ok")) {
                this.clientController.getChatView().removeMember(member);
                new ListMemberView(clientController).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, res);
            }
        }
    }

    private void kickButtonActionPerformed(ActionEvent evt) {
        String member = memberList.getSelectedValue();
        if (member != null) {
            String res = this.clientController.kickMember(member);
            if (res.equals("ok")) {
                this.clientController.getChatView().removeMember(member);
                new ListMemberView(clientController).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, res);
            }
        }
    }

    private void initComponents() {
        // btn kick, ban, back
        // show list of members, choose one to kick or ban

        javax.swing.JButton kickButton = new javax.swing.JButton();
        javax.swing.JButton banButton = new javax.swing.JButton();
        javax.swing.JButton backButton = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        memberList = new javax.swing.JList<>();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("List Member");
        setResizable(false);

        kickButton.setText("Kick");
        kickButton.addActionListener(this::kickButtonActionPerformed);
        banButton.setText("Ban");
        banButton.addActionListener(this::banButtonActionPerformed);
        backButton.setText("Back");
        backButton.addActionListener(this::backButtonActionPerformed);

        jScrollPane1.setViewportView(memberList);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(kickButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                                .addComponent(banButton)
                                                .addGap(30, 30, 30)
                                                .addComponent(backButton)
                                                .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(kickButton)
                                        .addComponent(banButton)
                                        .addComponent(backButton))
                                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }
}

