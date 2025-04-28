package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsulterController {

    @FXML
    private TextField searchField; // Titre

    @FXML
    private TextField keywordField; // Cotation (à clarifier)

    @FXML
    private TextField authorField; // Pas utilisé ici, mais on va l'utiliser pour "collection_id" ou laisser de côté

    @FXML
    private TextField minPriceField; // Prix minimum

    @FXML
    private TextField maxPriceField; // Prix maximum

    @FXML
    private Button searchButton;

    @FXML
    private ListView<String> resultsListView;

    @FXML
    private void initialize() {
        System.out.println("ConsulterController chargé !");
        searchButton.setOnAction(event -> handleSearch());
    }

    private void handleSearch() {
        String titre = searchField.getText();
        String cotation = keywordField.getText(); // Pas très clair à quoi il sert dans ta table
        String collectionId = authorField.getText();
        String minPrice = minPriceField.getText();
        String maxPrice = maxPriceField.getText();

        // Recherche dans la table objet !
        String query = "SELECT * FROM objet WHERE 1=1";

        if (!titre.isEmpty()) {
            query += " AND titre LIKE ?";
        }
        if (!cotation.isEmpty()) {
            query += " AND cotation LIKE ?";
        }
        if (!collectionId.isEmpty()) {
            query += " AND collection_id = ?";
        }
        if (!minPrice.isEmpty()) {
            query += " AND prix >= ?";
        }
        if (!maxPrice.isEmpty()) {
            query += " AND prix <= ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int index = 1;

            if (!titre.isEmpty()) {
                stmt.setString(index++, "%" + titre + "%");
            }
            if (!cotation.isEmpty()) {
                stmt.setString(index++, "%" + cotation + "%");
            }
            if (!collectionId.isEmpty()) {
                stmt.setInt(index++, Integer.parseInt(collectionId));
            }
            if (!minPrice.isEmpty()) {
                stmt.setDouble(index++, Double.parseDouble(minPrice));
            }
            if (!maxPrice.isEmpty()) {
                stmt.setDouble(index++, Double.parseDouble(maxPrice));
            }

            ResultSet rs = stmt.executeQuery();
            resultsListView.getItems().clear();

            while (rs.next()) {
                String result = rs.getString("titre") + " - " + rs.getDouble("prix") + "€ - " + rs.getDate("date_achat");
                resultsListView.getItems().add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

