package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    private User getUser(String email, String password) {
        String query = "SELECT nom, email, role FROM utilisateur WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String role = rs.getString("role");
                String mail = rs.getString("email");
                return new User(nom, mail, role);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("images/Register.jpg");
        if (brandingFile.exists()) {
            Image brandingImage = new Image(brandingFile.toURI().toString());
            brandingImageView.setImage(brandingImage);
        } else {
            System.out.println("Image non trouvée : " + brandingFile.getAbsolutePath());
        }
    }



    @FXML
    public void loginButtonAction(ActionEvent event) {
        try {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (email.isEmpty() || password.isEmpty()) {
                loginMessageLabel.setText("Veuillez remplir tous les champs.");
                return;
            }

            User user = getUser(email, password);

            if (user != null) {
                loginMessageLabel.setText("Connexion réussie !");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Dashboard.fxml"));
                Parent root = loader.load();

                Dashboard dashboardController = loader.getController();
                dashboardController.setUserData(
                        user.getNom(),
                        user.getEmail(),
                        java.time.LocalDateTime.now().toString()
                );

                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } else {
                loginMessageLabel.setText("Email ou mot de passe incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            loginMessageLabel.setText("Erreur de connexion.");
        }
    }


    private boolean validateLogin(String email, String password) {
        String query = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Retourne vrai si un utilisateur est trouvé

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public void cancelButtonAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void goToRegisterPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Register.fxml"));
            AnchorPane registerPage = loader.load();

            // Créez une nouvelle scène et l'affichez dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(registerPage));
            stage.setTitle("Register");
            stage.show();

            // Fermez la fenêtre de login si nécessaire
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
