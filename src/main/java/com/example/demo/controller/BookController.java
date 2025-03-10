package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        return new ResponseEntity<>(bookService.findByAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
        return new ResponseEntity<>(bookService.searchByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookService.findByIsbn(isbn);
        return book != null 
            ? new ResponseEntity<>(book, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        return new ResponseEntity<>(bookService.findAvailableBooks(), HttpStatus.OK);
    }

    @PutMapping("/{id}/quantity")
    public ResponseEntity<Book> updateBookQuantity(
            @PathVariable Long id,
            @RequestParam int quantity) {
        try {
            Book updatedBook = bookService.updateBookQuantity(id, quantity);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

/*
Explication détaillée du code :

1. Annotations principales :
   @RestController :
   - Combine @Controller et @ResponseBody
   - Indique que cette classe gère des requêtes REST
   - Convertit automatiquement les réponses en JSON

   @RequestMapping("/api/books") :
   - Définit le chemin de base pour tous les endpoints
   - Toutes les URLs commenceront par "/api/books"

2. Injection de dépendance :
   @Autowired :
   - Injecte le BookService
   - Spring gère l'instanciation et le cycle de vie

3. Endpoints REST :
   
   createBook (POST /) :
   - Crée un nouveau livre
   - Utilise @RequestBody pour désérialiser le JSON
   - Retourne HTTP 201 (CREATED)

   getBookById (GET /{id}) :
   - Récupère un livre par son ID
   - Utilise @PathVariable pour l'ID
   - Retourne 404 si non trouvé

   getAllBooks (GET /) :
   - Liste tous les livres
   - Retourne HTTP 200 (OK)

   getBooksByAuthor (GET /author/{author}) :
   - Trouve les livres par auteur
   - Utilise @PathVariable pour l'auteur

   searchBooks (GET /search?title=...) :
   - Recherche par titre
   - Utilise @RequestParam pour le paramètre de recherche

   getBookByIsbn (GET /isbn/{isbn}) :
   - Trouve un livre par ISBN
   - Retourne 404 si non trouvé

   getAvailableBooks (GET /available) :
   - Liste les livres disponibles

   updateBookQuantity (PUT /{id}/quantity) :
   - Met à jour la quantité d'un livre
   - Combine @PathVariable et @RequestParam

   deleteBook (DELETE /{id}) :
   - Supprime un livre
   - Retourne HTTP 204 (NO_CONTENT)

4. Gestion des réponses :
   - Utilisation de ResponseEntity pour contrôler :
     * Le code HTTP
     * Les headers
     * Le corps de la réponse
   - Gestion appropriée des cas d'erreur

Ce contrôleur est crucial car il :
- Expose l'API REST
- Gère la conversion JSON
- Implémente les bonnes pratiques REST
- Assure une gestion propre des erreurs
*/