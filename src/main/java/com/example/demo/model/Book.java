package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String isbn;

    @Column
    private String description;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean available = true;
}

/*
Explication détaillée du code :

1. Les annotations JPA :
   @Entity :
   - Indique que cette classe est une entité JPA
   - Sera automatiquement mappée à une table dans la base de données
   - Le nom de la table sera par défaut "book"

2. L'annotation Lombok @Data :
   - Génère automatiquement :
     * Les getters et setters
     * equals() et hashCode()
     * toString()
   - Réduit considérablement le code boilerplate

3. Les attributs et leurs annotations :
   @Id :
   - Marque le champ 'id' comme clé primaire
   
   @GeneratedValue :
   - Configure la génération automatique des IDs
   - IDENTITY signifie que la base de données gère l'auto-incrémentation

   @Column :
   - Personnalise le mapping des colonnes
   - nullable = false rend le champ obligatoire
   - Par défaut, le nom de la colonne sera le même que celui de l'attribut

4. Les champs :
   - id : Identifiant unique du livre
   - title : Titre du livre (obligatoire)
   - author : Auteur du livre (obligatoire)
   - isbn : Numéro ISBN unique (obligatoire)
   - description : Description du livre (optionnelle)
   - quantity : Nombre d'exemplaires disponibles
   - available : État de disponibilité du livre

Cette classe constitue le cœur du modèle de données pour les livres dans notre système de gestion de bibliothèque.
Elle définit la structure de la table 'book' dans la base de données et fournit une représentation orientée objet
pour manipuler les données des livres dans l'application.
*/