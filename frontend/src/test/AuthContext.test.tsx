import { describe, it, expect, beforeEach, afterEach } from 'vitest';
import React from 'react';
import { renderHook, act } from '@testing-library/react';
import { AuthProvider, useAuth } from '../context/AuthContext';
import { mockUsers } from './mocks';

describe('AuthContext', () => {
  beforeEach(() => {
    localStorage.clear();
  });

  afterEach(() => {
    localStorage.clear();
  });

  const wrapper = ({ children }: { children: React.ReactNode }) => (
    <AuthProvider>{children}</AuthProvider>
  );

  describe('Initial state', () => {
    it('should initialize with no user', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      expect(result.current.user).toBeNull();
      expect(result.current.isAuthenticated()).toBe(false);
      expect(result.current.loading).toBe(false);
    });

    it('should load user from localStorage if present', () => {
      localStorage.setItem('user', JSON.stringify(mockUsers.validUser));

      const { result } = renderHook(() => useAuth(), { wrapper });

      // Wait for useEffect to complete
      expect(result.current.user).toEqual(mockUsers.validUser);
      expect(result.current.isAuthenticated()).toBe(true);
    });
  });

  describe('login()', () => {
    it('should set user on login', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.testUser);
      });

      expect(result.current.user).toEqual(mockUsers.testUser);
      expect(result.current.isAuthenticated()).toBe(true);
    });

    it('should persist user in localStorage on login', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.validUser);
      });

      const storedUser = localStorage.getItem('user');
      expect(storedUser).not.toBeNull();
      expect(JSON.parse(storedUser!)).toEqual(mockUsers.validUser);
    });

    it('should handle multiple login calls', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.testUser);
      });

      expect(result.current.user?.email).toBe(mockUsers.testUser.email);

      act(() => {
        result.current.login(mockUsers.validUser);
      });

      expect(result.current.user?.email).toBe(mockUsers.validUser.email);
    });
  });

  describe('logout()', () => {
    it('should clear user on logout', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.testUser);
      });

      expect(result.current.user).not.toBeNull();

      act(() => {
        result.current.logout();
      });

      expect(result.current.user).toBeNull();
      expect(result.current.isAuthenticated()).toBe(false);
    });

    it('should clear localStorage on logout', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.validUser);
      });

      expect(localStorage.getItem('user')).not.toBeNull();

      act(() => {
        result.current.logout();
      });

      expect(localStorage.getItem('user')).toBeNull();
    });

    it('should handle logout when no user is logged in', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      expect(() => {
        act(() => {
          result.current.logout();
        });
      }).not.toThrow();

      expect(result.current.user).toBeNull();
    });
  });

  describe('isAuthenticated()', () => {
    it('should return false when no user', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      expect(result.current.isAuthenticated()).toBe(false);
    });

    it('should return true when user is logged in', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.testUser);
      });

      expect(result.current.isAuthenticated()).toBe(true);
    });

    it('should return false after logout', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.testUser);
        result.current.logout();
      });

      expect(result.current.isAuthenticated()).toBe(false);
    });
  });

  describe('User data integrity', () => {
    it('should preserve all user fields', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.validUser);
      });

      expect(result.current.user?.id).toBe(mockUsers.validUser.id);
      expect(result.current.user?.nom).toBe(mockUsers.validUser.nom);
      expect(result.current.user?.prenom).toBe(mockUsers.validUser.prenom);
      expect(result.current.user?.email).toBe(mockUsers.validUser.email);
      expect(result.current.user?.role).toBe(mockUsers.validUser.role);
    });

    it('should handle admin user correctly', () => {
      const { result } = renderHook(() => useAuth(), { wrapper });

      act(() => {
        result.current.login(mockUsers.adminUser);
      });

      expect(result.current.user?.role).toBe('ADMIN');
      expect(result.current.isAuthenticated()).toBe(true);
    });
  });

  describe('Error handling', () => {
    it('should throw error when useAuth is used outside AuthProvider', () => {
      expect(() => {
        renderHook(() => useAuth());
      }).toThrow('useAuth must be used within an AuthProvider');
    });
  });
});

