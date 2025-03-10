package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByAuthor(String author);
    
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    List<Book> findByAvailableTrue();
    
    @Query("SELECT b FROM Book b WHERE b.quantity > :minQuantity")
    List<Book> findBooksInStock(@Param("minQuantity") int minQuantity);
    
    Book findByIsbn(String isbn);
}

/*
Explication détaillée du code :

1. Interface et héritage :
   - L'interface étend JpaRepository<Book, Long>
   - Book : type de l'entité gérée
   - Long : type de l'identifiant de l'entité
   - Hérite automatiquement les méthodes CRUD standard :
     * save()
     * findById()
     * findAll()
     * delete()
     * etc.

2. Méthodes personnalisées :
   
   findByAuthor :
   - Recherche tous les livres d'un auteur donné
   - Spring Data JPA génère automatiquement l'implémentation
   
   findByTitleContainingIgnoreCase :
   - Recherche les livres dont le titre contient la chaîne spécifiée
   - Ignore la casse (majuscules/minuscules)
   
   findByAvailableTrue :
   - Retourne tous les livres disponibles
   - Utilise le champ 'available' de l'entité Book
   
   findBooksInStock :
   - Utilise @Query pour une requête JPQL personnalisée
   - Trouve les livres dont la quantité dépasse un seuil
   - @Param lie le paramètre Java au paramètre de requête
   
   findByIsbn :
   - Trouve un livre par son numéro ISBN
   - Retourne un seul livre car ISBN est unique

3. Fonctionnalités importantes :
   - Génération automatique des requêtes par Spring Data
   - Support des transactions automatique
   - Gestion optimisée de la connexion à la base de données
   - Mise en cache automatique des requêtes fréquentes

Ce repository est crucial car il :
- Fournit une couche d'abstraction pour l'accès aux données
- Simplifie les opérations CRUD
- Permet des requêtes personnalisées efficaces
- Suit le pattern Repository de Domain-Driven Design




JPA Repository est une interface fournie par Spring Data JPA qui simplifie considérablement l'accès aux données. Voici une explication détaillée :

Concept Fondamental

JpaRepository est une interface générique : JpaRepository<T, ID>
T : Type de l'entité (dans notre cas, Book)
ID : Type de l'identifiant (dans notre cas, Long)
Méthodes Héritées Automatiquement

CRUD basique :

save(entity)           // Créer ou mettre à jour
findById(id)          // Trouver par ID
findAll()             // Récupérer tous
delete(entity)        // Supprimer
count()               // Compter les entrées
Génération Dynamique de Requêtes

Spring crée automatiquement les implémentations basées sur le nom des méthodes
Exemple : findByAuthor(String author)
Spring génère : SELECT * FROM book WHERE author = ?
Conventions de Nommage


findBy[Propriété]                    // Égalité exacte
findBy[Propriété]Containing          // LIKE %value%
findBy[Propriété]IgnoreCase          // Insensible à la casse
findBy[Propriété]OrderBy[Propriété]  // Avec tri
Requêtes Personnalisées


@Query("SELECT b FROM Book b WHERE b.quantity > :minQuantity")
List<Book> findBooksInStock(@Param("minQuantity") int minQuantity);
Avantages Principaux

Réduction du code boilerplate
Gestion automatique des transactions
Optimisation des performances
Abstraction de la base de données
Exemple d'Utilisation


@Autowired
private BookRepository bookRepository;

// Utilisation
Book book = bookRepository.findById(1L).orElse(null);
List<Book> books = bookRepository.findByAuthor("Victor Hugo");
C'est un outil puissant qui simplifie énormément le développement en éliminant le besoin d'écrire du code répétitif pour l'accès aux données.








*/
