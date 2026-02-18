import { User } from '../types';
import apiClient from './apiClient';

/**
 * Service d'authentification pour les patients
 */
const authService = {
  /**
   * Authentifie un patient avec son email et son téléphone
   * @param email - Email du patient
   * @param telephone - Numéro de téléphone du patient (mot de passe)
   * @returns Données du patient authentifié
   */
  login: async (email: string, telephone: string): Promise<User> => {
    try {
      const response = await apiClient.post<User>('/auth/login', {
        email,
        telephone,
      });
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 401) {
        throw new Error('Email ou mot de passe incorrect');
      }
      throw new Error('Erreur lors de la connexion. Veuillez réessayer.');
    }
  },
};

export default authService;

