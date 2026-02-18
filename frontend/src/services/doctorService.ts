import axios, { AxiosInstance } from 'axios';
import { Doctor, PageResponse } from '../types';

const API_BASE_URL = 'http://localhost:8080';

const apiClient: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

interface GetDoctorsParams {
  page?: number;
  size?: number;
  sortBy?: string;
  sortDirection?: string;
  search?: string;
  speciality?: string;
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

