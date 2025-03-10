package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

/*
Explication du code :

1. @SpringBootApplication :
   - C'est une annotation qui combine trois autres annotations :
     * @Configuration : Indique que la classe peut définir des beans Spring
     * @EnableAutoConfiguration : Active la configuration automatique de Spring Boot
     * @ComponentScan : Permet de scanner automatiquement les composants dans le package

2. @EnableJpaRepositories :
   - Active le support des repositories JPA
   - Permet à Spring de créer automatiquement les implémentations des interfaces repository

3. La méthode main() :
   - C'est le point d'entrée de l'application
   - SpringApplication.run() démarre l'application Spring Boot
   - Configure le conteneur Spring
   - Lance le serveur web embarqué
   - Prépare l'environnement

Ce fichier est crucial car il :
- Initialise l'application Spring Boot
- Configure les composants essentiels
- Permet la découverte automatique des repositories, services et controllers
*/