# Cabinet Médical

Application de gestion de cabinet médical développée avec Spring Boot et React.

> **⚠️ Configuration requise**: L'application utilise **PostgreSQL** comme base de données. Assurez-vous que PostgreSQL est installé et configuré avant de démarrer l'application.

## 📝 Fonctionnalités

En tant que patient:
- Je peux m'authentifier avec mon email et mon téléphone
- Je peux consulter la liste des médecins disponibles
- Chaque médecin est associé à une spécialité
- Je peux sélectionner un médecin ainsi que visualiser les créneaux de ses rendez-vous disponibles
- Je peux réserver un créneau horaire disponible
- Chaque créneau ne peut être réservé que par un seul patient

## 🔐 Authentification

L'authentification patient se fait via :
- **Login** : Email du patient
- **Mot de passe** : Numéro de téléphone du patient

📖 **Voir le guide complet** : [AUTHENTICATION_GUIDE.md](AUTHENTICATION_GUIDE.md)

### Exemple de compte de test
- **Email** : `marie.durand@email.fr`
- **Téléphone** : `0601020304`

## 📁 Structure du Projet

```
CABINET_MEDICAL/
├── backend/                    # Application backend Java 17 + Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/cabinetmedical/
│   │   │   │       ├── CabinetMedicalApplication.java
│   │   │   │       ├── controller/      # AuthController, DoctorController
│   │   │   │       ├── entity/          # Patient, Doctor, Speciality
│   │   │   │       ├── repository/      # PatientRepository, DoctorRepository
│   │   │   │       ├── service/         # AuthService, DoctorService
│   │   │   │       ├── dto/             # LoginRequestDTO, LoginResponseDTO, DoctorDTO
│   │   │   │       ├── mapper/
│   │   │   │       └── exception/
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       ├── liquibase.properties
│   │   │       └── db/changelog/
│   │   │           └── changes/
│   │   │               ├── 001-initial-schema.xml
│   │   │               ├── 002-seed-data.xml
│   │   │               ├── 003-seed-medecins.xml
│   │   │               └── 004-seed-patients.xml
│   │   └── test/
│   └── pom.xml
├── frontend/                   # Application frontend React
│   ├── src/
│   │   ├── components/         # DoctorCard, PrivateRoute
│   │   ├── context/           # AuthContext
│   │   ├── pages/             # LoginPage, DoctorsPage
│   │   ├── services/          # authService, doctorService
│   │   ├── styles/            # CSS files
│   │   └── main.jsx
│   └── package.json
├── AUTHENTICATION_GUIDE.md     # Guide d'authentification
├── start-app.ps1              # Script de démarrage automatique
├── test-auth.ps1              # Script de test de l'authentification
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

### Frontend
- **React 16.14.0** - Bibliothèque UI
- **React Router DOM 5.3.4** - Routing
- **Axios 1.6.0** - Client HTTP
- **Vite 5.0.0** - Build tool
- **Vitest 1.0.0** - Framework de test
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
- PostgreSQL 16 (requis)

## 🚀 Démarrage Rapide

### 1. Installer et Configurer PostgreSQL

#### Installation

1. **Télécharger PostgreSQL 16**
   - Site officiel: https://www.postgresql.org/download/windows/
   - Ou directement: https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

2. **Installer avec ces paramètres**:
   - Port: `5432`
   - Username: `postgres`
   - Password: `0000` (ou votre choix - à retenir!)
   - Locale: `French, France` ou `English, United States`

3. **Vérifier l'installation**:
   ```powershell
   # Vérifier le service PostgreSQL
   Get-Service | Where-Object { $_.DisplayName -like "*postgres*" }
   
   # Tester la connexion au port
   Test-NetConnection -ComputerName localhost -Port 5432
   ```

#### Créer la base de données

```powershell
# Naviguer vers le répertoire bin de PostgreSQL
cd "C:\Program Files\PostgreSQL\16\bin"

# Se connecter à PostgreSQL (entrez le mot de passe quand demandé)
.\psql.exe -U postgres

# Dans l'invite psql, créer la base:
CREATE DATABASE cabinet_medical;

# Vérifier que la base existe:
\l

# Se connecter à la base:
\c cabinet_medical

# Quitter:
\q
```

#### Configurer l'application

Si vous avez utilisé un mot de passe différent de `0000`, modifiez:

`backend/src/main/resources/application.properties`:
```properties
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

### 2. Compiler et Démarrer le Backend

```powershell
# Compiler le projet
cd backend
mvn clean install

# Lancer l'application
mvn spring-boot:run
```

L'application démarrera sur: http://localhost:8080

Au premier démarrage, Liquibase créera automatiquement toutes les tables et insérera les données de test (médecins et patients).

### 3. Démarrer le Frontend

Dans un nouveau terminal :

```powershell
# Installer les dépendances
cd frontend
npm install

# Lancer le serveur de développement
npm run dev
```

L'application frontend démarrera sur: http://localhost:5173

Vous pouvez maintenant vous connecter avec un compte patient (voir [AUTHENTICATION_GUIDE.md](AUTHENTICATION_GUIDE.md)).

## 🎯 Scripts PowerShell

Pour faciliter le développement, des scripts PowerShell sont disponibles :

### Démarrage automatique de l'application
```powershell
# Démarre le backend et le frontend automatiquement
.\start-app.ps1
```

Ce script :
- Compile le backend
- Démarre le backend sur http://localhost:8080
- Installe les dépendances du frontend
- Démarre le frontend sur http://localhost:5173
- Ouvre deux fenêtres PowerShell séparées

### Test de l'API d'authentification
```powershell
# Teste l'endpoint de login avec un compte de test
.\test-auth.ps1
```

Ce script :
- Vérifie que le backend est accessible
- Teste l'authentification avec un compte valide
- Teste le rejet des identifiants invalides
- Affiche les résultats des tests

## 🌐 Accès aux Services

Une fois l'application démarrée :

| Service | URL | Description |
|---------|-----|-------------|
| **Frontend** | http://localhost:5173 | Application React (page de login) |
| **API Backend** | http://localhost:8080 | API REST Spring Boot |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | Documentation interactive de l'API |
| **Health Check** | http://localhost:8080/actuator/health | Statut de l'application |
| **OpenAPI Docs** | http://localhost:8080/api-docs | Spécification OpenAPI JSON |

### Endpoints principaux

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/auth/login` | Authentification patient (email + téléphone) |
| GET | `/doctor/allDoctors` | Liste paginée des médecins avec leurs spécialités |


## 🗄️ Base de Données

### Configuration PostgreSQL

```yaml
Host: localhost
Port: 5432
Database: cabinet_medical
Username: postgres
Password: 0000
```

### Commandes utiles

```bash
# Se connecter à la base (si psql dans PATH)
psql -U postgres -d cabinet_medical

# Ou avec le chemin complet
cd "C:\Program Files\PostgreSQL\16\bin"
.\psql.exe -U postgres -d cabinet_medical

# Lister les tables
\dt

# Décrire une table
\d nom_table

# Quitter psql
\q
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

### Erreur: "authentification par mot de passe échouée pour l'utilisateur 'postgres'"

C'est l'erreur la plus courante. Plusieurs solutions:

#### Solution 1: Vérifier/Changer le mot de passe dans application.properties

1. Ouvrez `backend/src/main/resources/application.properties`
2. Trouvez la ligne `spring.datasource.password`
3. Remplacez `0000` par votre mot de passe PostgreSQL réel

```properties
spring.datasource.password=VOTRE_MOT_DE_PASSE_ICI
```

#### Solution 2: Créer la base de données manuellement

```powershell
# Ouvrir psql en tant que postgres
cd "C:\Program Files\PostgreSQL\16\bin"
.\psql.exe -U postgres

# Dans psql, créer la base:
CREATE DATABASE cabinet_medical;

# Vérifier que la base existe:
\l

# Quitter:
\q
```

#### Solution 3: Réinitialiser le mot de passe PostgreSQL

```powershell
# Ouvrir psql
cd "C:\Program Files\PostgreSQL\16\bin"
.\psql.exe -U postgres

# Changer le mot de passe (remplacez 0000 par le mot de passe souhaité):
ALTER USER postgres PASSWORD '0000';

# Quitter:
\q
```

Puis relancez l'application.

### Port 8080 déjà utilisé

Changez le port dans `backend/src/main/resources/application.properties` :

```properties
server.port=8081
```

### Base de données non accessible

Vérifiez que PostgreSQL est démarré:
1. Ouvrez les Services Windows (Win+R, puis `services.msc`)
2. Cherchez le service `postgresql-x64-16` (ou version similaire)
3. S'il n'est pas démarré, faites un clic droit > Démarrer

Ou redémarrez le service en PowerShell:
```powershell
# En tant qu'administrateur
Restart-Service postgresql-x64-16
```

### Problème de compilation

```bash
cd backend
mvn clean install
```

### Problème avec Liquibase

Si Liquibase échoue, vous pouvez:

1. **Désactiver Liquibase temporairement** dans `application.properties`:
```properties
spring.liquibase.enabled=false
```

2. **Créer les tables manuellement** via psql ou un outil comme pgAdmin

3. **Supprimer les tables Liquibase** pour réinitialiser:
```sql
DROP TABLE IF EXISTS databasechangelog CASCADE;
DROP TABLE IF EXISTS databasechangeloglock CASCADE;
```

## 📄 License

Ce projet est sous licence privée.

## 👥 Contributeurs

- Équipe Cabinet Médical

## 📞 Support

Pour toute question, consultez la documentation dans le projet.
