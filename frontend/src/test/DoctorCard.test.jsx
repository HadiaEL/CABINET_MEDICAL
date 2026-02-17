import { describe, it, expect } from 'vitest';
import React from 'react';
import { render, screen } from '@testing-library/react';
import DoctorCard from '../components/DoctorCard';

describe('DoctorCard', () => {
  const mockDoctor = {
    id: 1,
    nom: 'Dupont',
    prenom: 'Jean',
    email: 'jean.dupont@example.com',
    telephone: '0123456789',
    adresse: '123 Rue de la Santé, Paris',
    speciality: {
      id: 1,
      nom: 'CARDIOLOGUE',
    },
  };

  it('renders doctor information correctly', () => {
    render(<DoctorCard doctor={mockDoctor} />);

    expect(screen.getByText(/Dr. Dupont Jean/i)).toBeInTheDocument();
    expect(screen.getByText('CARDIOLOGUE')).toBeInTheDocument();
    expect(screen.getByText('jean.dupont@example.com')).toBeInTheDocument();
    expect(screen.getByText('0123456789')).toBeInTheDocument();
    expect(screen.getByText('123 Rue de la Santé, Paris')).toBeInTheDocument();
  });

  it('renders appointment button', () => {
    render(<DoctorCard doctor={mockDoctor} />);

    const button = screen.getByRole('button', { name: /prendre rendez-vous/i });
    expect(button).toBeInTheDocument();
  });

  it('renders without speciality', () => {
    const doctorWithoutSpeciality = { ...mockDoctor, speciality: null };
    render(<DoctorCard doctor={doctorWithoutSpeciality} />);

    expect(screen.getByText(/Dr. Dupont Jean/i)).toBeInTheDocument();
    expect(screen.queryByText('CARDIOLOGUE')).not.toBeInTheDocument();
  });
});

