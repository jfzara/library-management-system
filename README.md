# Library Management System

REST API for managing a library's book catalogue, built with Spring Boot and PostgreSQL. Work in progress: only the book module exists so far, and the React frontend is still the default Vite template.

Personal learning project (March 2025), started to practise Java and Spring Boot. `LibraryManagementSystem.txt` holds the planning notes (use cases, data model, business rules) for the full scope; most of that scope is not implemented yet.

<!-- TODO: live URL -->

## Features

Implemented (backend, `/api/books`):

- Create a book: `POST /api/books`
- List all books: `GET /api/books`
- Get a book by id: `GET /api/books/{id}`
- Get a book by ISBN: `GET /api/books/isbn/{isbn}`
- List books by author: `GET /api/books/author/{author}`
- Search by title, case-insensitive: `GET /api/books/search?title=...`
- List available books: `GET /api/books/available`
- Update stock: `PUT /api/books/{id}/quantity?quantity=N` (also sets `available` to `quantity > 0`)
- Delete a book: `DELETE /api/books/{id}`
- HTTP Basic authentication with two in-memory accounts (`user` with role USER, `admin` with roles USER and ADMIN); passwords hashed with BCrypt.

Not implemented yet (planned in `LibraryManagementSystem.txt`): members, loans and returns, reservations, fines, admin dashboard, tests, API documentation, and the frontend.

## Tech stack

- Java 17, Spring Boot 3.2 (Web, Data JPA, Validation, Security)
- PostgreSQL, Hibernate
- Lombok
- Maven
- Frontend folder: React 19, TypeScript, Vite (template only; `axios`, `react-hot-toast`, `lucide-react` and Tailwind CSS are installed but not used yet)

## Architecture

Standard layered Spring Boot structure under `src/main/java/com/example/demo/`:

- `controller/BookController` — HTTP endpoints.
- `service/BookService` — business logic, `@Transactional`.
- `repository/BookRepository` — Spring Data JPA interface with derived queries (`findByAuthor`, `findByTitleContainingIgnoreCase`, `findByAvailableTrue`, `findByIsbn`) and one JPQL query.
- `model/Book` — JPA entity (`id`, `title`, `author`, `isbn`, `description`, `quantity`, `available`).
- `config/SecurityConfig` — security filter chain and in-memory users.

`library-management-frontend/` is a separate Vite project.

## Key decisions

- **Stateless HTTP Basic instead of sessions or JWT.** Simple to test with curl or Postman while the API takes shape; CSRF is disabled because there is no session cookie. The trade-off is that credentials travel with every request, and the users are hard-coded in memory.
- **Spring Data derived queries rather than hand-written SQL.** Search and filter methods are declared by name in the repository, which keeps the data layer short.
- **Availability derived from stock.** Updating the quantity also updates the `available` flag, so the two fields cannot drift apart through that endpoint.

## Getting started

Requirements: Java 17, Maven, PostgreSQL.

1. Create a PostgreSQL database named `library_db`. The committed configuration expects PostgreSQL on port `5433`.
2. Check `application.properties` at the repository root (server port, datasource URL, username and password) and adjust it to your local setup. Hibernate creates and updates the tables (`ddl-auto=update`).
3. Run the API from the repository root, so Spring Boot picks up that file:

```bash
git clone https://github.com/jfzara/library-management-system.git
cd library-management-system
mvn spring-boot:run      # http://localhost:8080
```

Example request:

```bash
curl http://localhost:8080/api/books
```

Frontend (template only):

```bash
cd library-management-frontend
npm install
npm run dev              # http://localhost:5173
```

## Known limitations

- Security rules are incomplete: the `/api/books` matcher is `permitAll` for every HTTP method, so `POST /api/books` (create) is public, and any authenticated user, not only an admin, can delete a book.
- Demo user passwords are hard-coded in `SecurityConfig`.
- The ISBN column is not unique in the entity, although `findByIsbn` assumes it is.
- No input validation on the entity, no global exception handling, no tests.
- `src/resources/application.properties` is empty and sits outside the standard `src/main/resources/` folder; the configuration actually used is the one at the repository root.
- Several Java files end with long explanatory comment blocks written while learning.

## Author

Jeff Zara — [GitHub](https://github.com/jfzara)

---

## Français

# Système de gestion de bibliothèque

API REST pour gérer le catalogue de livres d'une bibliothèque, construite avec Spring Boot et PostgreSQL. Projet en cours : seul le module des livres existe pour l'instant, et le frontend React est encore le gabarit Vite par défaut.

Projet personnel d'apprentissage (mars 2025), commencé pour pratiquer Java et Spring Boot. `LibraryManagementSystem.txt` contient les notes de planification (cas d'utilisation, modèle de données, règles métier) pour la portée complète ; la plus grande partie n'est pas encore réalisée.

<!-- TODO: live URL -->

### Fonctionnalités

Réalisé (backend, `/api/books`) :

- Créer un livre : `POST /api/books`
- Lister tous les livres : `GET /api/books`
- Obtenir un livre par identifiant : `GET /api/books/{id}`
- Obtenir un livre par ISBN : `GET /api/books/isbn/{isbn}`
- Lister les livres d'un auteur : `GET /api/books/author/{author}`
- Rechercher par titre, sans tenir compte de la casse : `GET /api/books/search?title=...`
- Lister les livres disponibles : `GET /api/books/available`
- Mettre à jour le stock : `PUT /api/books/{id}/quantity?quantity=N` (met aussi `available` à `quantity > 0`)
- Supprimer un livre : `DELETE /api/books/{id}`
- Authentification HTTP Basic avec deux comptes en mémoire (`user` avec le rôle USER, `admin` avec les rôles USER et ADMIN) ; mots de passe hachés avec BCrypt.

Pas encore réalisé (prévu dans `LibraryManagementSystem.txt`) : membres, emprunts et retours, réservations, amendes, tableau de bord administrateur, tests, documentation de l'API et frontend.

### Technologies

- Java 17, Spring Boot 3.2 (Web, Data JPA, Validation, Security)
- PostgreSQL, Hibernate
- Lombok
- Maven
- Dossier frontend : React 19, TypeScript, Vite (gabarit seulement ; `axios`, `react-hot-toast`, `lucide-react` et Tailwind CSS sont installés mais pas encore utilisés)

### Architecture

Structure en couches classique de Spring Boot, sous `src/main/java/com/example/demo/` :

- `controller/BookController` — points d'accès HTTP.
- `service/BookService` — logique métier, `@Transactional`.
- `repository/BookRepository` — interface Spring Data JPA avec requêtes dérivées (`findByAuthor`, `findByTitleContainingIgnoreCase`, `findByAvailableTrue`, `findByIsbn`) et une requête JPQL.
- `model/Book` — entité JPA (`id`, `title`, `author`, `isbn`, `description`, `quantity`, `available`).
- `config/SecurityConfig` — chaîne de filtres de sécurité et utilisateurs en mémoire.

`library-management-frontend/` est un projet Vite distinct.

### Choix techniques

- **HTTP Basic sans état plutôt que sessions ou JWT.** Simple à tester avec curl ou Postman pendant que l'API prend forme ; la protection CSRF est désactivée puisqu'il n'y a pas de cookie de session. En contrepartie, les identifiants accompagnent chaque requête et les utilisateurs sont codés en dur en mémoire.
- **Requêtes dérivées de Spring Data plutôt que du SQL écrit à la main.** Les méthodes de recherche et de filtre sont déclarées par leur nom dans le repository, ce qui garde la couche de données courte.
- **Disponibilité déduite du stock.** La mise à jour de la quantité met aussi à jour l'indicateur `available`, pour que les deux champs ne divergent pas par ce point d'accès.

### Démarrage

Prérequis : Java 17, Maven, PostgreSQL.

1. Créer une base PostgreSQL nommée `library_db`. La configuration versionnée attend PostgreSQL sur le port `5433`.
2. Vérifier `application.properties` à la racine du dépôt (port du serveur, URL de la source de données, utilisateur et mot de passe) et l'adapter à votre environnement. Hibernate crée et met à jour les tables (`ddl-auto=update`).
3. Lancer l'API depuis la racine du dépôt, pour que Spring Boot lise ce fichier :

```bash
git clone https://github.com/jfzara/library-management-system.git
cd library-management-system
mvn spring-boot:run      # http://localhost:8080
```

Exemple de requête :

```bash
curl http://localhost:8080/api/books
```

Frontend (gabarit seulement) :

```bash
cd library-management-frontend
npm install
npm run dev              # http://localhost:5173
```

### Limites connues

- Les règles de sécurité sont incomplètes : la règle `/api/books` est en `permitAll` pour toutes les méthodes HTTP, donc `POST /api/books` (création) est public, et n'importe quel utilisateur authentifié, pas seulement un administrateur, peut supprimer un livre.
- Les mots de passe des comptes de démonstration sont codés en dur dans `SecurityConfig`.
- La colonne ISBN n'est pas unique dans l'entité, alors que `findByIsbn` suppose qu'elle l'est.
- Pas de validation des entrées sur l'entité, pas de gestion globale des exceptions, pas de tests.
- `src/resources/application.properties` est vide et se trouve hors du dossier standard `src/main/resources/` ; la configuration réellement utilisée est celle à la racine du dépôt.
- Plusieurs fichiers Java se terminent par de longs blocs de commentaires explicatifs écrits pendant l'apprentissage.

### Auteur

Jeff Zara — [GitHub](https://github.com/jfzara)
