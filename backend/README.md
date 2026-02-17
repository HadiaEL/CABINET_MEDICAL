# Cabinet Médical - Backend

Application backend Java 17 pour la gestion d'un cabinet médical.

## Technologies utilisées

- **Java 17**
- **Spring Boot 3.2.2**
- **PostgreSQL** - Base de données
- **Liquibase** - Gestion des migrations de base de données
- **MapStruct** - Mapping entre entités et DTOs
- **Swagger/OpenAPI** - Documentation de l'API REST
- **Maven** - Gestion des dépendances
- **Lombok** - Réduction du code boilerplate

## Prérequis

- Java 17 ou supérieur
- Maven 3.8+
- Docker et Docker Compose (pour la base de données PostgreSQL)

## Installation

### 1. Démarrer la base de données PostgreSQL

```bash
# Depuis la racine du projet
cd ..
docker-compose up -d
cd backend
```

Cela va démarrer :
- PostgreSQL sur le port 5432 (pour le développement)
- PostgreSQL sur le port 5433 (pour les tests)

### 2. Compiler le projet

```bash
mvn clean install
```

Ou utilisez le script batch fourni (Windows) :

```bash
build.bat
```

### 3. Lancer l'application

```bash
mvn spring-boot:run
```

Ou utilisez le script batch fourni (Windows) qui démarre aussi la base de données :

```bash
run.bat
```

Ou avec un profil spécifique :

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Accès à l'application

- **API REST** : http://localhost:8080/api
- **Swagger UI** : http://localhost:8080/api/swagger-ui.html
- **OpenAPI Docs** : http://localhost:8080/api/api-docs

## Configuration

### Profils disponibles

- **default** : Configuration par défaut
- **dev** : Profil de développement
- **prod** : Profil de production
- **test** : Profil pour les tests

### Configuration de la base de données

La configuration se trouve dans `src/main/resources/application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cabinet_medical
    username: postgres
    password: postgres
```

## Structure du projet

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── cabinetmedical/
│   │   │           ├── CabinetMedicalApplication.java  # Classe principale
│   │   │           ├── config/
│   │   │           │   └── OpenApiConfig.java          # Configuration Swagger
│   │   │           ├── controller/
│   │   │           │   └── HealthController.java       # Controller de santé
│   │   │           ├── entity/                         # Pour les entités JPA
│   │   │           ├── repository/                     # Pour les repositories
│   │   │           ├── service/                        # Pour les services
│   │   │           ├── dto/                            # Pour les DTOs
│   │   │           ├── mapper/                         # Pour les mappers MapStruct
│   │   │           └── exception/                      # Pour la gestion des erreurs
│   │   └── resources/
│   │       ├── application.yml                         # Configuration principale
│   │       ├── application-dev.yml                     # Configuration dev
│   │       ├── application-prod.yml                    # Configuration prod
│   │       └── db/
│   │           └── changelog/
│   │               └── db.changelog-master.xml         # Changelog Liquibase
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── cabinetmedical/
│       │           └── CabinetMedicalApplicationTests.java
│       └── resources/
│           └── application-test.yml                    # Configuration tests
├── pom.xml
├── build.bat
├── run.bat
└── verify.bat
```

## Liquibase

Les migrations de base de données sont gérées par Liquibase. Les fichiers de changelog se trouvent dans `src/main/resources/db/changelog/`.

Pour ajouter une nouvelle migration, créez un fichier XML dans `db/changelog/changes/` et incluez-le dans `db.changelog-master.xml`.

## MapStruct

MapStruct est configuré pour la génération automatique des mappers. Les mappers doivent être des interfaces annotées avec `@Mapper(componentModel = "spring")`.

## Tests

Lancer les tests :

```bash
mvn test
```

## Build

Créer un package JAR :

```bash
mvn clean package
```

Le JAR sera créé dans `target/cabinet-medical-1.0.0-SNAPSHOT.jar`

## Commandes utiles

```bash
# Nettoyer le projet
mvn clean

# Compiler sans exécuter les tests
mvn clean install -DskipTests

# Vérifier les dépendances
mvn dependency:tree
```

## Prochaines étapes

L'application est maintenant prête. Vous pouvez ajouter :
- Des entités JPA
- Des repositories
- Des services
- Des mappers MapStruct
- Des controllers REST
- Des migrations Liquibase

## License

Ce projet est sous licence privée.

