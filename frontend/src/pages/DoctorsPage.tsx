import React, { useState, useEffect, FormEvent, ChangeEvent } from 'react';
import { useHistory } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { getDoctors } from '../services/doctorService';
import { getAllSpecialities } from '../services/specialityService';
import DoctorCard from '../components/DoctorCard';
import { Specialite } from '../types';
import '../styles/DoctorsPage.css';

interface Doctor {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  numeroOrdre: string;
  specialite: {
    id: number;
    nom: string;
    description: string;
  };
}

interface FetchParams {
  page: number;
  search?: string;
  speciality?: string;
}

const DoctorsPage: React.FC = () => {
  const [doctors, setDoctors] = useState<Doctor[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>('');
  const [page, setPage] = useState<number>(0);
  const [totalPages, setTotalPages] = useState<number>(0);
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [selectedSpeciality, setSelectedSpeciality] = useState<string>('');
  const [specialities, setSpecialities] = useState<Specialite[]>([]);

  const { user, logout } = useAuth();
  const history = useHistory();

  // Fetch specialities on mount
  useEffect(() => {
    const fetchSpecialities = async () => {
      try {
        const data = await getAllSpecialities();
        setSpecialities(data);
      } catch (err) {
        console.error('Erreur lors du chargement des spécialités:', err);
      }
    };
    fetchSpecialities();
  }, []);

  useEffect(() => {
    fetchDoctors();
  }, [page, searchTerm, selectedSpeciality]);

  const fetchDoctors = async (): Promise<void> => {
    setLoading(true);
    setError('');
    try {
      const params: FetchParams = {
        page,
      };

      if (searchTerm) {
        params.search = searchTerm;
      }

      if (selectedSpeciality) {
        params.speciality = selectedSpeciality;
      }

      const response = await getDoctors(params);
      setDoctors(response.content || []);
      setTotalPages(response.totalPages || 0);
    } catch (err) {
      setError('Erreur lors du chargement des médecins');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = (): void => {
    logout();
    history.push('/login');
  };

  const handleSearch = (e: FormEvent<HTMLFormElement>): void => {
    e.preventDefault();
    setPage(0);
    fetchDoctors();
  };

  const handlePageChange = (newPage: number): void => {
    if (newPage >= 0 && newPage < totalPages) {
      setPage(newPage);
    }
  };

  return (
    <div className="doctors-page">
      <header className="page-header">
        <div className="header-content">
          <h1>Liste des Médecins</h1>
          <div className="user-info">
            <span>Bienvenue, {user?.email}</span>
            <button onClick={handleLogout} className="logout-button">
              Déconnexion
            </button>
          </div>
        </div>
      </header>

      <div className="page-content">
        <div className="search-section">
          <form onSubmit={handleSearch} className="search-form">
            <input
              type="text"
              placeholder="Rechercher un médecin..."
              value={searchTerm}
              onChange={(e: ChangeEvent<HTMLInputElement>) => setSearchTerm(e.target.value)}
              className="search-input"
            />
            <select
              value={selectedSpeciality}
              onChange={(e: ChangeEvent<HTMLSelectElement>) => setSelectedSpeciality(e.target.value)}
              className="speciality-select"
            >
              <option value="">Toutes les spécialités</option>
              {specialities.map((specialite) => (
                <option key={specialite.id} value={specialite.nom}>
                  {specialite.nom}
                </option>
              ))}
            </select>
            <button type="submit" className="search-button">
              Rechercher
            </button>
          </form>
        </div>

        {error && <div className="error-message">{error}</div>}

        {loading ? (
          <div className="loading">Chargement des médecins...</div>
        ) : (
          <>
            <div className="doctors-grid">
              {doctors.length > 0 ? (
                doctors.map((doctor) => (
                  <DoctorCard key={doctor.id} doctor={doctor} />
                ))
              ) : (
                <div className="no-results">Aucun médecin trouvé</div>
              )}
            </div>

            {totalPages > 1 && (
              <div className="pagination">
                <button
                  onClick={() => handlePageChange(page - 1)}
                  disabled={page === 0}
                  className="pagination-button"
                >
                  Précédent
                </button>
                <span className="pagination-info">
                  Page {page + 1} sur {totalPages}
                </span>
                <button
                  onClick={() => handlePageChange(page + 1)}
                  disabled={page >= totalPages - 1}
                  className="pagination-button"
                >
                  Suivant
                </button>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default DoctorsPage;

