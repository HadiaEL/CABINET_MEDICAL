/ Types pour l'application Cabinet Médical

export interface User {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: string;
}

export interface Specialite {
  id: number;
  nom: string;
  description: string;
}

export interface Doctor {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  numeroOrdre: string;
  specialite: Specialite;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  pageSize: number;
  pageNumber: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}

export interface LoginRequest {
  email: string;
  telephone: string;
}

export interface LoginResponse extends User {}

