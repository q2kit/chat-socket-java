package org.main;

import org.views.LoginView;
import org.views.HomeView;
import org.controller.ClientController;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            try {
                String src = "C:\\Users\\" + System.getProperty("user.name") + "\\.chatapp\\token.txt";
                File file = new File(src);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String token = br.readLine();
                if (token != null) {
                    ClientController clientController = new ClientController(token);
                    clientController.startThread();
                    new HomeView(clientController).setVisible(true);
                } else {
                    ClientController clientController = new ClientController();
                    clientController.startThread();
                    new LoginView(clientController).setVisible(true);
                }
                br.close();
            } catch (Exception e) {
                ClientController clientController = new ClientController();
                clientController.startThread();
                new LoginView(clientController).setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}