import React from 'react';
import '../styles/DoctorCard.css';

interface Speciality {
  id: number;
  nom: string;
  description: string;
}

interface Doctor {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  telephone: string;
  numeroOrdre: string;
  speciality?: Speciality;
  adresse?: string;
}

interface DoctorCardProps {
  doctor: Doctor;
}

const DoctorCard: React.FC<DoctorCardProps> = ({ doctor }) => {
  return (
    <div className="doctor-card">
      <div className="doctor-header">
        <h3>Dr. {doctor.nom} {doctor.prenom}</h3>
        {doctor.speciality && (
          <span className="speciality-badge">
            {doctor.speciality.nom}
          </span>
        )}
      </div>

      <div className="doctor-info">
        {doctor.email && (
          <div className="info-item">
            <span className="info-label">Email:</span>
            <span className="info-value">{doctor.email}</span>
          </div>
        )}

        {doctor.telephone && (
          <div className="info-item">
            <span className="info-label">Téléphone:</span>
            <span className="info-value">{doctor.telephone}</span>
          </div>
        )}

        {doctor.adresse && (
          <div className="info-item">
            <span className="info-label">Adresse:</span>
            <span className="info-value">{doctor.adresse}</span>
          </div>
        )}
      </div>

      <div className="doctor-actions">
        <button className="btn-primary">Prendre rendez-vous</button>
      </div>
    </div>
  );
};

export default DoctorCard;

