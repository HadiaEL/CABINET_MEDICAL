// Re-export all types from separate files
export * from './userType';
export * from './doctorType';
export * from './specialityType';

export type PageResponse<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  pageSize: number;
  pageNumber: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}
