# Cabinet Médical - Frontend

Application frontend React 16 pour le système de gestion de cabinet médical.

## Fonctionnalités

- **Authentification Patient**: Page de connexion sécurisée pour les patients
- **Liste des Médecins**: Affichage paginé de tous les médecins disponibles
- **Recherche et Filtrage**: Recherche par nom et filtrage par spécialité
- **Design Responsive**: Interface adaptative pour tous les appareils

## Technologies

- **React 16.14.0**: Bibliothèque UI
- **React Router DOM 5.3.4**: Gestion des routes
- **Vite 5**: Build tool et dev server
- **Vitest**: Framework de tests
- **Axios**: Client HTTP pour les appels API

## Installation

```bash
npm install
```

## Démarrage

### Mode développement
```bash
npm run dev
```
L'application sera accessible sur `http://localhost:3000`

### Build production
```bash
npm run build
```

### Preview production
```bash
npm run preview
```

## Tests

### Lancer tous les tests
```bash
npm test
```

### Tests avec interface UI
```bash
npm run test:ui
```

### Coverage
```bash
npm run test:coverage
```

## Structure du Projet

```
frontend/
├── src/
│   ├── components/        # Composants réutilisables
│   │   ├── DoctorCard.jsx
│   │   └── PrivateRoute.jsx
│   ├── context/          # Context API pour l'état global
│   │   └── AuthContext.jsx
│   ├── pages/            # Pages principales
│   │   ├── LoginPage.jsx
│   │   └── DoctorsPage.jsx
│   ├── services/         # Services API
│   │   └── doctorService.js
│   ├── styles/           # Fichiers CSS
│   │   ├── index.css
│   │   ├── LoginPage.css
│   │   ├── DoctorsPage.css
│   │   └── DoctorCard.css
│   ├── test/             # Tests unitaires
│   │   ├── setup.js
│   │   ├── LoginPage.test.jsx
│   │   ├── DoctorCard.test.jsx
│   │   └── AuthContext.test.jsx
│   ├── App.jsx           # Composant racine
│   └── main.jsx          # Point d'entrée
├── index.html
├── vite.config.js
└── package.json
```

## Configuration

### Proxy API
Le proxy Vite est configuré pour rediriger les appels `/api` vers `http://localhost:8080` (backend Spring Boot).

Vous pouvez modifier cette configuration dans `vite.config.js`.

## Utilisation

### Connexion
1. Accédez à la page de connexion (`/login`)
2. Entrez votre email et mot de passe
3. Cliquez sur "Se connecter"

*Note: Pour la démo, tout email/mot de passe valide est accepté*

### Consultation des Médecins
1. Après connexion, vous serez redirigé vers `/doctors`
2. Utilisez la barre de recherche pour trouver un médecin spécifique
3. Filtrez par spécialité avec le menu déroulant
4. Naviguez entre les pages avec les boutons de pagination

## API Backend

L'application communique avec le backend Spring Boot via les endpoints suivants:

- `GET /doctor/allDoctors`: Liste des médecins (avec pagination et filtres)
- `GET /doctor/allDoctors/{id}`: Détails d'un médecin spécifique

## Authentification

L'authentification est gérée via le Context API de React:
- Les données utilisateur sont stockées dans `localStorage`
- Les routes protégées redirigent vers `/login` si non authentifié
- La déconnexion efface les données utilisateur

## Contribution

1. Créez une branche pour votre fonctionnalité
2. Committez vos changements
3. Créez une Pull Request

## License

Propriétaire - Cabinet Médical

