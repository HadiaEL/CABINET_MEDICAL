# Cabinet Médical

Application de gestion de cabinet médical avec architecture monorepo.

## 📝 Fonctionnalités

En tant que patient:
- Je peux consulter la liste des médecins disponibles
- Chaque médecin est associé à une spécialité
- Je peux sélectionner un médecin ainsi que visualiser les créneaux de ses rendez-vous disponibles
- Je peux réserver un créneau horaire disponible
- Chaque créneau ne peut être réservé que par un seul patient

## 📁 Structure du Projet

```
CABINET_MEDICAL/
├── backend/                    # Application backend Java 17 + Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/cabinetmedical/
│   │   │   │       ├── CabinetMedicalApplication.java
│   │   │   │       ├── config/
│   │   │   │       ├── controller/
│   │   │   │       ├── entity/
│   │   │   │       ├── repository/
│   │   │   │       ├── service/
│   │   │   │       ├── dto/
│   │   │   │       ├── mapper/
│   │   │   │       └── exception/
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── db/changelog/
│   │   └── test/
│   ├── pom.xml
│   ├── build.bat
│   ├── run.bat
│   └── README.md
├── docker-compose.yml          # Configuration PostgreSQL
└── README.md                   # Ce fichier
```

## 🛠️ Technologies

### Backend
- **Java 17** - Langage de programmation
- **Spring Boot 3.2.2** - Framework web
- **PostgreSQL 16** - Base de données
- **Liquibase** - Gestion des migrations de base de données
- **MapStruct 1.5.5** - Mapping entre entités et DTOs
- **Swagger/OpenAPI 2.3.0** - Documentation de l'API REST
- **Maven** - Gestion des dépendances
- **Lombok 1.18.30** - Réduction du code boilerplate

## 📦 Prérequis

- Java 17 ou supérieur
- Maven 3.8+
- Docker et Docker Compose

## 🚀 Démarrage Rapide

### 1. Démarrer la base de données PostgreSQL

```bash
docker-compose up -d
```

Cela va démarrer :
- PostgreSQL sur le port 5432 (pour le développement)
- PostgreSQL sur le port 5433 (pour les tests)

### 2. Démarrer le Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Ou avec le script batch (Windows) :

```bash
cd backend
run.bat
```

## 🌐 Accès aux Services

Une fois l'application démarrée :

| Service | URL |
|---------|-----|
| **API Backend** | http://localhost:8080/api |
| **Swagger UI** | http://localhost:8080/api/swagger-ui.html |
| **Health Check** | http://localhost:8080/api/health |
| **OpenAPI Docs** | http://localhost:8080/api/api-docs |

## 📚 Documentation

- [Documentation Backend](backend/README.md) - Guide complet du backend Java/Spring Boot

## 🗄️ Base de Données

### Configuration PostgreSQL

```yaml
Host: localhost
Port: 5432
Database: cabinet_medical
Username: postgres
Password: postgres
```

### Commandes Docker

```bash
# Démarrer les conteneurs
docker-compose up -d

# Arrêter les conteneurs
docker-compose down

# Voir les logs
docker-compose logs -f postgres

# Se connecter à la base
docker exec -it cabinet_medical_db psql -U postgres -d cabinet_medical
```

## 🔧 Configuration

### Profils disponibles

- **default** : Configuration par défaut
- **dev** : Profil de développement (logs détaillés)
- **prod** : Profil de production (logs minimaux)
- **test** : Profil pour les tests (base séparée)

### Activer un profil

```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 🏗️ Architecture

### Backend (Couches)

```
Controller (API REST)
    ↓
Service (Logique métier)
    ↓
Repository (Accès données)
    ↓
Entity (Modèle de données)

DTO ←→ Mapper (MapStruct) ←→ Entity
```

## 📝 Développement

### Backend

1. Les entités JPA vont dans `backend/src/main/java/com/cabinetmedical/entity/`
2. Les repositories dans `backend/src/main/java/com/cabinetmedical/repository/`
3. Les services dans `backend/src/main/java/com/cabinetmedical/service/`
4. Les DTOs dans `backend/src/main/java/com/cabinetmedical/dto/`
5. Les mappers dans `backend/src/main/java/com/cabinetmedical/mapper/`
6. Les controllers dans `backend/src/main/java/com/cabinetmedical/controller/`

### Migrations Liquibase

Les migrations de base de données sont dans :
- `backend/src/main/resources/db/changelog/`

Pour ajouter une migration :
1. Créer un fichier XML dans `db/changelog/changes/`
2. L'inclure dans `db.changelog-master.xml`

## 🧪 Tests

```bash
cd backend
mvn test
```

## 📦 Build

```bash
cd backend
mvn clean package
```

Le JAR sera créé dans `backend/target/cabinet-medical-1.0.0-SNAPSHOT.jar`

## 🐛 Dépannage

### Port 8080 déjà utilisé

Changez le port dans `backend/src/main/resources/application.yml` :

```yaml
server:
  port: 8081
```

### Base de données non accessible

```bash
# Vérifier que Docker tourne
docker ps

# Redémarrer PostgreSQL
docker-compose restart
```

### Problème de compilation

```bash
cd backend
mvn clean
mvn clean install
```

## 📄 License

Ce projet est sous licence privée.

## 👥 Contributeurs

- Équipe Cabinet Médical

## 📞 Support

Pour toute question, consultez la documentation dans le dossier `backend/`.
