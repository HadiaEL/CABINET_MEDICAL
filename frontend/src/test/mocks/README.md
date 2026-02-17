# 📦 Mocks pour les Tests

Ce dossier contient toutes les données mock utilisées dans les tests du frontend.

## 📁 Structure

```
mocks/
├── index.ts           # Point d'entrée centralisé
├── userMocks.ts       # Mocks des utilisateurs et identifiants
└── doctorMocks.ts     # Mocks des médecins et spécialités
```

## 🔧 Utilisation

### Import simple
```typescript
import { mockUsers, mockDoctors, mockCredentials } from './mocks';
```

### Import spécifique
```typescript
import { mockDoctors } from './mocks/doctorMocks';
import { mockUsers } from './mocks/userMocks';
```

## 📋 Données disponibles

### 1. User Mocks (`userMocks.ts`)

#### `mockUsers`
- `validUser` - Utilisateur patient valide (Marie Durand)
- `testUser` - Utilisateur de test générique
- `adminUser` - Utilisateur administrateur

#### `mockCredentials`
- `valid` - Identifiants valides pour l'authentification
  - Email: `marie.durand@email.fr`
  - Téléphone: `0601020304`
- `invalid` - Identifiants invalides pour tester les erreurs
- `test` - Identifiants de test génériques

### 2. Doctor Mocks (`doctorMocks.ts`)

#### `mockSpecialities`
- `cardiology` - Spécialité Cardiologue
- `generalPractice` - Spécialité Médecine Générale
- `pediatrics` - Spécialité Pédiatre
- `dermatology` - Spécialité Dermatologue

#### `mockDoctors`
- `cardiologist` - Dr. Jean Dupont (Cardiologue)
- `generalPractitioner` - Dr. Sophie Martin (Médecine Générale)
- `pediatrician` - Dr. Luc Bernard (Pédiatre)
- `withoutSpeciality` - Dr. Julie Moreau (sans spécialité)

#### Listes et réponses paginées
- `mockDoctorsList` - Liste de tous les médecins (sans ceux sans spécialité)
- `mockPageResponse` - Réponse paginée complète
- `mockEmptyPageResponse` - Réponse paginée vide

## 💡 Exemples d'utilisation

### Test d'authentification
```typescript
import { mockUsers, mockCredentials } from './mocks';

it('should login with valid credentials', () => {
  const user = mockUsers.validUser;
  const credentials = mockCredentials.valid;
  
  // Utiliser dans le test...
});
```

### Test de carte médecin
```typescript
import { mockDoctors } from './mocks';

it('should display doctor card', () => {
  render(<DoctorCard doctor={mockDoctors.cardiologist} />);
  // Assertions...
});
```

### Test de liste paginée
```typescript
import { mockPageResponse } from './mocks';

it('should display paginated doctors', () => {
  mockApiCall.mockResolvedValue(mockPageResponse);
  // Test...
});
```

## 🎯 Avantages

✅ **Centralisation** - Toutes les données de test au même endroit  
✅ **Réutilisabilité** - Mêmes données dans tous les tests  
✅ **Maintenabilité** - Un seul endroit à modifier  
✅ **Typage** - TypeScript pour la sécurité des types  
✅ **Cohérence** - Données cohérentes entre les tests  

## 🔄 Mise à jour

Pour ajouter de nouveaux mocks :

1. Ajouter les données dans le fichier approprié (`userMocks.ts` ou `doctorMocks.ts`)
2. Exporter les nouvelles données
3. Les importer dans `index.ts` si nécessaire
4. Utiliser dans les tests

## 📝 Conventions

- **Nommage** : Utiliser `mock` comme préfixe (ex: `mockUsers`)
- **Structure** : Organiser par entité (users, doctors, etc.)
- **Types** : Toujours typer les données avec les interfaces appropriées
- **Documentation** : Commenter les données particulières

