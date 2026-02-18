import { Doctor, PageResponse } from '../types';
import apiClient from './apiClient';

interface GetDoctorsParams {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDirection?: string;
  search?: string;
  specialityId?: number;
}

/**
 * Récupère la liste paginée des médecins
 * @param params - Paramètres de pagination et tri
 * @returns Réponse paginée avec la liste des médecins
 */
export const getDoctors = async (params: GetDoctorsParams = {}): Promise<PageResponse<Doctor>> => {
  try {
    const queryParams: any = {
      page: params.page || 0,
      sortBy: params.sortBy || 'nom',
      sortDirection: params.sortDirection || 'asc',
    };

    // Only include size if explicitly provided
    if (params.size !== undefined) {
      queryParams.size = params.size;
    }

    // Only include specialityId if explicitly provided
    if (params.specialityId !== undefined) {
      queryParams.specialityId = params.specialityId;
    }

    const response = await apiClient.get<PageResponse<Doctor>>('/doctor/allDoctors', {
      params: queryParams
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching doctors:', error);
    throw error;
  }
};

/**
 * Récupère un médecin par son ID
 * @param id - Identifiant du médecin
 * @returns Données du médecin
 */
export const getDoctorById = async (id: number): Promise<Doctor> => {
  try {
    const response = await apiClient.get<Doctor>(`/doctor/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching doctor:', error);
    throw error;
  }
};

export default {
  getDoctors,
  getDoctorById,
};

