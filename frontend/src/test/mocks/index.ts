// Index des mocks - Point d'entrée centralisé pour tous les mocks de test

export * from './userMocks';
export * from './doctorMocks';

// Réexportation par défaut
export { default as userMocks } from './userMocks';
export { default as doctorMocks } from './doctorMocks';

