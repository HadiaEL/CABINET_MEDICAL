import { Specialite } from '../types';
import apiClient from './apiClient';

/**
 * Récupère la liste de toutes les spécialités médicales
 * @returns Liste des spécialités triées par nom
 */
export const getAllSpecialities = async (): Promise<Specialite[]> => {
  try {
    const response = await apiClient.get<Specialite[]>('/speciality/allSpecialities');
    return response.data;
  } catch (error) {
    console.error('Error fetching specialities:', error);
    throw error;
  }
};

export default {
  getAllSpecialities,
};

