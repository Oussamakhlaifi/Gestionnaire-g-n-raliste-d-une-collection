package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Dashboard {

    @FXML
    private Label userNameLabel;

    @FXML
    private Label userEmailLabel;

    @FXML
    private Label loginDateLabel;

    @FXML
    private Button ajouterCollectionButton;

    @FXML
    private Button consulterCollectionButton;

    @FXML
    private Button ajouterObjetButton;

    @FXML
    private Button consulterObjetButton;

    @FXML
    private Button logoutButton;

    // Méthode pour initialiser les infos utilisateur
    public void setUserData(String nom, String email, String dateConnexion) {
        userNameLabel.setText("Nom : " + nom);
        userEmailLabel.setText("Email : " + email);
        loginDateLabel.setText("Connecté depuis : " + dateConnexion);
    }

    @FXML
    private void initialize() {
        ajouterCollectionButton.setOnAction(e -> {
            // Logique pour aller à la page "Ajouter une Collection"
            System.out.println("Ajouter une collection cliqué !");
        });

        consulterCollectionButton.setOnAction(e -> {
            // Logique pour aller à la page "Consulter les Collections"
            System.out.println("Consulter les collections cliqué !");
        });

        ajouterObjetButton.setOnAction(e -> {
            // Logique pour aller à la page "Ajouter un Objet"
            System.out.println("Ajouter un objet cliqué !");
        });

        consulterObjetButton.setOnAction(e -> {
            // Logique pour aller à la page "Consulter les Objets"
            System.out.println("Consulter les objets cliqué !");
        });

        logoutButton.setOnAction(e -> handleLogout());
    }

    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
