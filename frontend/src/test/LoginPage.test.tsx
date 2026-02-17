import { describe, it, expect } from 'vitest';
import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
import { AuthProvider } from '../context/AuthContext';
import { mockCredentials } from './mocks';

const MockedLoginPage: React.FC = () => (
  <BrowserRouter>
    <AuthProvider>
      <LoginPage />
    </AuthProvider>
  </BrowserRouter>
);

describe('LoginPage', () => {
  it('renders login form', () => {
    render(<MockedLoginPage />);

    expect(screen.getByText('Cabinet Médical')).toBeInTheDocument();
    expect(screen.getByText('Connexion Patient')).toBeInTheDocument();
    expect(screen.getByLabelText('Email')).toBeInTheDocument();
    expect(screen.getByLabelText(/Téléphone/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /se connecter/i })).toBeInTheDocument();
  });

  it('allows user to type in email and telephone fields', () => {
    render(<MockedLoginPage />);

    const emailInput = screen.getByLabelText('Email') as HTMLInputElement;
    const telephoneInput = screen.getByLabelText(/Téléphone/i) as HTMLInputElement;

    fireEvent.change(emailInput, { target: { value: mockCredentials.valid.email } });
    fireEvent.change(telephoneInput, { target: { value: mockCredentials.valid.telephone } });

    expect(emailInput.value).toBe(mockCredentials.valid.email);
    expect(telephoneInput.value).toBe(mockCredentials.valid.telephone);
  });

  it('shows error when submitting empty form', () => {
    render(<MockedLoginPage />);

    const submitButton = screen.getByRole('button', { name: /se connecter/i });
    fireEvent.click(submitButton);

    // HTML5 validation will prevent submission
    const emailInput = screen.getByLabelText('Email') as HTMLInputElement;
    expect(emailInput).toBeRequired();
  });

  it('displays all form elements correctly', () => {
    render(<MockedLoginPage />);

    // Vérifier les labels
    expect(screen.getByLabelText('Email')).toBeInTheDocument();
    expect(screen.getByLabelText(/Téléphone \(mot de passe\)/i)).toBeInTheDocument();

    // Vérifier les placeholders
    const emailInput = screen.getByPlaceholderText('votre.email@exemple.com');
    const telephoneInput = screen.getByPlaceholderText('0601020304');

    expect(emailInput).toBeInTheDocument();
    expect(telephoneInput).toBeInTheDocument();

    // Vérifier le footer
    expect(screen.getByText(/Connectez-vous pour consulter la liste des médecins/i)).toBeInTheDocument();
  });
});

