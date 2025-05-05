package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardCansultant {

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Label loginDateLabel;

    public void initializeDashboard(String name, String email, String loginTime) {
        userNameLabel.setText("Nom : " + name);
        userEmailLabel.setText("Email : " + email);
        loginDateLabel.setText("Connecté depuis : " + loginTime);
    }
}
