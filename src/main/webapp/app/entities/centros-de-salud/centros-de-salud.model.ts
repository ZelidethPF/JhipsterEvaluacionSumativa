export interface ICentrosDeSalud {
  id: number;
  nombre?: string | null;
  tipo?: string | null;
  ciudad?: string | null;
  direccion?: string | null;
  telefono?: string | null;
  email?: string | null;
}

export type NewCentrosDeSalud = Omit<ICentrosDeSalud, 'id'> & { id: null };
