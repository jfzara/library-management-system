package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }
    
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
    
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    
    public List<Book> findAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }
    
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    
    public Book updateBookQuantity(Long id, int quantity) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setQuantity(quantity);
            book.setAvailable(quantity > 0);
            return bookRepository.save(book);
        }
        throw new RuntimeException("Book not found with id: " + id);
    }
}

/*
Explication détaillée du code :

1. Annotations Spring :
   @Service :
   - Marque la classe comme composant de service Spring
   - Permet l'injection automatique de dépendances
   
   @Transactional :
   - Gère automatiquement les transactions
   - Assure l'intégrité des données
   - Rollback automatique en cas d'exception

2. Injection de dépendance :
   @Autowired :
   - Injecte automatiquement le BookRepository
   - Spring gère le cycle de vie du repository

3. Méthodes de service :
   
   saveBook :
   - Sauvegarde ou met à jour un livre
   - Utilise le repository.save()
   
   findById :
   - Recherche un livre par son ID
   - Retourne un Optional<Book>
   
   findAllBooks :
   - Récupère tous les livres
   - Utilise repository.findAll()
   
   findByAuthor :
   - Trouve les livres d'un auteur
   - Délègue au repository
   
   searchByTitle :
   - Recherche par titre (insensible à la casse)
   - Utilise la méthode repository correspondante
   
   findByIsbn :
   - Trouve un livre par ISBN
   - ISBN étant unique, retourne un seul livre
   
   findAvailableBooks :
   - Liste tous les livres disponibles
   
   deleteBook :
   - Supprime un livre par son ID
   
   updateBookQuantity :
   - Met à jour la quantité d'un livre
   - Gère aussi le statut de disponibilité
   - Lance une exception si le livre n'existe pas

4. Gestion des erreurs :
   - Utilisation d'Optional pour gérer les nulls
   - Exceptions appropriées pour les cas d'erreur
   - Transactions automatiques pour la cohérence

Ce service est essentiel car il :
- Encapsule la logique métier
- Gère les transactions
- Fournit une API claire pour les contrôleurs
- Ajoute une couche d'abstraction sur le repository



La couche Service dans Spring Boot est une partie cruciale de l'architecture en couches, située entre les contrôleurs (API) et les repositories (accès aux données). Voici ses principales responsabilités :

Logique Métier

Encapsule toutes les règles métier de l'application
Valide les données avant traitement
Coordonne les opérations complexes impliquant plusieurs repositories
Gestion des Transactions

L'annotation @Transactional assure l'intégrité des données
Garantit que les opérations sont atomiques (tout ou rien)
Gère automatiquement les rollbacks en cas d'erreur
Abstraction

Isole la logique métier de l'accès aux données
Simplifie les tests unitaires
Permet de changer l'implémentation du repository sans impacter les contrôleurs
Sécurité

Point central pour implémenter les contrôles d'accès
Validation des permissions utilisateur
Filtrage des données sensibles
Dans notre BookService, on voit ces concepts appliqués :


@Service  // Marque la classe comme un composant service
@Transactional  // Gère les transactions automatiquement
public class BookService {
    @Autowired
    private BookRepository bookRepository;  // Injection de dépendance

    // Méthode avec logique métier
    public Book updateBookQuantity(Long id, int quantity) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setQuantity(quantity);
            // Logique métier : mise à jour de la disponibilité
            book.setAvailable(quantity > 0);
            return bookRepository.save(book);
        }
        throw new RuntimeException("Book not found with id: " + id);
    }
}
Cette organisation permet :

Une meilleure maintenabilité du code
Une séparation claire des responsabilités
Une réutilisation facile de la logique métier
Une gestion centralisée des transactions et de la sécurité

*/