import dayjs from 'dayjs/esm';

export interface IAgendamiento {
  id: number;
  paciente?: string | null;
  medico?: string | null;
  centro?: string | null;
  fechahora?: dayjs.Dayjs | null;
  estado?: string | null;
  origen?: string | null;
  motivo?: string | null;
  fechaingreso?: dayjs.Dayjs | null;
  nivelprioridad?: string | null;
}

export type NewAgendamiento = Omit<IAgendamiento, 'id'> & { id: null };
