// Mock data pour les tests - Utilisateurs
export const mockUsers = {
  validUser: {
    id: 1,
    nom: 'Durand',
    prenom: 'Marie',
    email: 'marie.durand@email.fr',
    role: 'PATIENT',
  },

  testUser: {
    id: 2,
    nom: 'Test',
    prenom: 'User',
    email: 'test@example.com',
    role: 'PATIENT',
  },

  adminUser: {
    id: 3,
    nom: 'Admin',
    prenom: 'System',
    email: 'admin@cabinet.fr',
    role: 'ADMIN',
  },
};

// Identifiants de connexion pour les tests
export const mockCredentials = {
  valid: {
    email: 'marie.durand@email.fr',
    telephone: '0601020304',
  },

  invalid: {
    email: 'invalid@email.fr',
    telephone: '0000000000',
  },

  test: {
    email: 'test@example.com',
    password: 'password123',
  },
};

export default {
  mockUsers,
  mockCredentials,
};

