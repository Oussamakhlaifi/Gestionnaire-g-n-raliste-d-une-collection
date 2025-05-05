package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        // Appeler la méthode pour insérer en base
        register(firstName, lastName, email, password);
    }

    // Méthode pour insérer l'utilisateur dans la base
    public void register(String firstName, String lastName, String email, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Connexion à la base
            connection = DatabaseConnection.getConnection();

            // Requête SQL d'insertion
            String sql = "INSERT INTO utilisateur (nom, prenom, email,mot_de_passe) VALUES (?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password); // ⚠️ à crypter en production !

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie !");
                redirectToLogin();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'inscription.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'inscription : " + e.getMessage());
        } finally {
            // Toujours fermer la connexion et la requête
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Méthode pour deriger vers la page login
    @FXML
    private  void redirectToLogin(){
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("Login.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.scene.Scene scene = firstnameTextField.getScene();
            scene.setRoot(root);
        } catch (Exception e){
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur" , "Erreur lors de la redirection vers la page de connexion : " + e.getMessage());
        }
    }
}
