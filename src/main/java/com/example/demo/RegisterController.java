package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField emailnameTextField;
    @FXML
    private PasswordField passwordnameTextField;
    @FXML
    private PasswordField confirmPasswordnameTextField;

    @FXML
    private void handleRegister() {
        // Récupérer les informations des champs
        String firstName = firstnameTextField.getText();
        String lastName = lastnameTextField.getText();
        String email = emailnameTextField.getText();
        String password = passwordnameTextField.getText();
        String confirmPassword = confirmPasswordnameTextField.getText();

        // Vérifier si tous les champs sont remplis
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs manquants", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.WARNING, "Mots de passe non correspondants", "Les mots de passe ne correspondent pas.");
            return;
        }

        // Insertion des données (simulation)
        // Vous pouvez insérer dans votre base de données ou effectuer d'autres opérations ici
        System.out.println("Inscription réussie !");
        System.out.println("Prénom: " + firstName);
        System.out.println("Nom: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Mot de passe: " + password);

        // Message de succès
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie !");
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
