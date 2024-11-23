export interface IMedicos {
  id: number;
  nombre?: string | null;
  rut?: string | null;
  especialidad?: string | null;
  telefono?: string | null;
  ciudad?: string | null;
  email?: string | null;
  estado?: boolean | null;
}

export type NewMedicos = Omit<IMedicos, 'id'> & { id: null };
