export type User = {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: string;
}

export type LoginRequest = {
  email: string;
  telephone: string;
}

export type LoginResponse = User;
