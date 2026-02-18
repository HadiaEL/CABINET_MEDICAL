import axios, { AxiosInstance } from 'axios';

/**
 * Configuration de base pour tous les appels API
 */
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

/**
 * Instance Axios configurée et réutilisable pour tous les services
 * Utilise une configuration centralisée avec baseURL et headers communs
 */
const apiClient: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Intercepteur de requête (optionnel)
 * Peut être utilisé pour ajouter des tokens d'authentification, etc.
 */
apiClient.interceptors.request.use(
  (config) => {
    // Exemple : Ajouter un token d'authentification si disponible
    // const token = localStorage.getItem('authToken');
    // if (token) {
    //   config.headers.Authorization = `Bearer ${token}`;
    // }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

/**
 * Intercepteur de réponse (optionnel)
 * Peut être utilisé pour gérer les erreurs globalement
 */
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    // Gérer les erreurs globales ici
    if (error.response?.status === 401) {
      // Redirection vers login, par exemple
      console.error('Non autorisé - Veuillez vous reconnecter');
    }
    return Promise.reject(error);
  }
);

export default apiClient;

