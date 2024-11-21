import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'dd364c13-92f7-4814-98f0-454e2e6f4664',
};

export const sampleWithPartialData: IAuthority = {
  name: '0d8b74a2-c186-4f99-9be7-160b74984fde',
};

export const sampleWithFullData: IAuthority = {
  name: '79429dc4-2ff1-4fa5-a273-0dd734d8a982',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
