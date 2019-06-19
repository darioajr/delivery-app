export const enum SiglaEstado {
  AC = 'AC',
  AL = 'AL',
  AP = 'AP',
  AM = 'AM',
  BA = 'BA',
  CE = 'CE',
  DF = 'DF',
  ES = 'ES',
  GO = 'GO',
  MA = 'MA',
  MT = 'MT',
  MS = 'MS',
  MG = 'MG',
  PA = 'PA',
  PB = 'PB',
  PR = 'PR',
  PE = 'PE',
  PI = 'PI',
  RJ = 'RJ',
  RN = 'RN',
  RS = 'RS',
  RO = 'RO',
  RR = 'RR',
  SC = 'SC',
  SP = 'SP',
  SE = 'SE',
  TO = 'TO'
}

export interface ICidade {
  id?: number;
  nome?: string;
  estado?: SiglaEstado;
  empresaId?: number;
  motoboyId?: number;
}

export class Cidade implements ICidade {
  constructor(
    public id?: number,
    public nome?: string,
    public estado?: SiglaEstado,
    public empresaId?: number,
    public motoboyId?: number
  ) {}
}
