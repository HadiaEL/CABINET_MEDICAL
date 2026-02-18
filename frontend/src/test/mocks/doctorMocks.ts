// Mock data pour les tests - Médecins et Spécialités

export const mockSpecialities = {
  cardiology: {
    id: 1,
    nom: 'CARDIOLOGUE',
    description: 'Médecin spécialiste du cœur et du système cardiovasculaire',
  },

  generalPractice: {
    id: 2,
    nom: 'MÉDECINE GÉNÉRALE',
    description: 'Médecin généraliste pour consultations générales',
  },

  pediatrics: {
    id: 3,
    nom: 'PÉDIATRE',
    description: 'Médecin spécialiste des enfants',
  },

  dermatology: {
    id: 4,
    nom: 'DERMATOLOGUE',
    description: 'Médecin spécialiste de la peau',
  },
};

export const mockDoctors = {
  cardiologist: {
    id: 1,
    nom: 'Dupont',
    prenom: 'Jean',
    email: 'jean.dupont@example.com',
    telephone: '0123456789',
    numeroOrdre: 'ORD-00001',
    adresse: '123 Rue de la Santé, Paris',
    specialite: mockSpecialities.cardiology,
  },

  generalPractitioner: {
    id: 2,
    nom: 'Martin',
    prenom: 'Sophie',
    email: 'sophie.martin@cabinet.fr',
    telephone: '0123456790',
    numeroOrdre: 'ORD-00002',
    adresse: '45 Avenue des Champs, Lyon',
    specialite: mockSpecialities.generalPractice,
  },

  pediatrician: {
    id: 3,
    nom: 'Bernard',
    prenom: 'Luc',
    email: 'luc.bernard@cabinet.fr',
    telephone: '0604050607',
    numeroOrdre: 'ORD-00003',
    adresse: '23 Rue du Commerce, Nantes',
    specialite: mockSpecialities.pediatrics,
  },

  withoutSpeciality: {
    id: 4,
    nom: 'Moreau',
    prenom: 'Julie',
    email: 'julie.moreau@cabinet.fr',
    telephone: '0605060708',
    numeroOrdre: 'ORD-00004',
    adresse: '67 Rue Gambetta, Bordeaux',
    specialite: null,
  },
};

export const mockDoctorsList = Object.values(mockDoctors).filter(
  (doctor) => doctor.specialite !== null
);

export const mockPageResponse = {
  content: mockDoctorsList,
  totalElements: mockDoctorsList.length,
  totalPages: 1,
  pageSize: 9,
  pageNumber: 0,
  first: true,
  last: true,
  empty: false,
};

export const mockEmptyPageResponse = {
  content: [],
  totalElements: 0,
  totalPages: 0,
  pageSize: 9,
  pageNumber: 0,
  first: true,
  last: true,
  empty: true,
};

export default {
  mockSpecialities,
  mockDoctors,
  mockDoctorsList,
  mockPageResponse,
  mockEmptyPageResponse,
};

