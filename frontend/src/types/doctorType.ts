import { Specialite } from './specialityType';

export type Doctor = {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  numeroOrdre: string;
  specialite: Specialite;
}