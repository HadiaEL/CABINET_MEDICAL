/**
 * Point d'entrée centralisé pour tous les services API
 * Facilite les imports et maintient une structure cohérente
 */

export { default as apiClient } from './apiClient';
export { default as authService } from './authService';
export * from './doctorService';
export * from './specialityService';

