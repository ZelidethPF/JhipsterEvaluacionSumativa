export interface IPaciente {
  id: number;
  nombre?: string | null;
  rut?: string | null;
  edad?: number | null;
  sexo?: string | null;
  direccion?: string | null;
  ciudad?: string | null;
  telefono?: string | null;
  email?: string | null;
}

export type NewPaciente = Omit<IPaciente, 'id'> & { id: null };
