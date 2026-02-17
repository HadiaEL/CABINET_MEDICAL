import { describe, it, expect } from 'vitest';
import React from 'react';
import { render, screen } from '@testing-library/react';
import DoctorCard from '../components/DoctorCard';
import { mockDoctors } from './mocks';

describe('DoctorCard', () => {
  it('renders doctor information correctly', () => {
    render(<DoctorCard doctor={mockDoctors.cardiologist} />);

    expect(screen.getByText(/Dr. Dupont Jean/i)).toBeInTheDocument();
    expect(screen.getByText('CARDIOLOGUE')).toBeInTheDocument();
    expect(screen.getByText('jean.dupont@example.com')).toBeInTheDocument();
    expect(screen.getByText('0123456789')).toBeInTheDocument();
    expect(screen.getByText('123 Rue de la Santé, Paris')).toBeInTheDocument();
  });

  it('renders appointment button', () => {
    render(<DoctorCard doctor={mockDoctors.cardiologist} />);

    const button = screen.getByRole('button', { name: /prendre rendez-vous/i });
    expect(button).toBeInTheDocument();
  });

  it('renders without speciality', () => {
    render(<DoctorCard doctor={mockDoctors.withoutSpeciality} />);

    expect(screen.getByText(/Dr. Moreau Julie/i)).toBeInTheDocument();
    expect(screen.queryByText('CARDIOLOGUE')).not.toBeInTheDocument();
  });

  it('renders general practitioner correctly', () => {
    render(<DoctorCard doctor={mockDoctors.generalPractitioner} />);

    expect(screen.getByText(/Dr. Martin Sophie/i)).toBeInTheDocument();
    expect(screen.getByText('MÉDECINE GÉNÉRALE')).toBeInTheDocument();
    expect(screen.getByText('sophie.martin@cabinet.fr')).toBeInTheDocument();
    expect(screen.getByText('0123456790')).toBeInTheDocument();
  });

  it('renders pediatrician correctly', () => {
    render(<DoctorCard doctor={mockDoctors.pediatrician} />);

    expect(screen.getByText(/Dr. Bernard Luc/i)).toBeInTheDocument();
    expect(screen.getByText('PÉDIATRE')).toBeInTheDocument();
    expect(screen.getByText('luc.bernard@cabinet.fr')).toBeInTheDocument();
  });

  it('renders all doctor info fields when present', () => {
    render(<DoctorCard doctor={mockDoctors.cardiologist} />);

    // Vérifier les labels
    expect(screen.getByText('Email:')).toBeInTheDocument();
    expect(screen.getByText('Téléphone:')).toBeInTheDocument();
    expect(screen.getByText('Adresse:')).toBeInTheDocument();

    // Vérifier les valeurs
    expect(screen.getByText(mockDoctors.cardiologist.email)).toBeInTheDocument();
    expect(screen.getByText(mockDoctors.cardiologist.telephone)).toBeInTheDocument();
    expect(screen.getByText(mockDoctors.cardiologist.adresse!)).toBeInTheDocument();
  });
});

