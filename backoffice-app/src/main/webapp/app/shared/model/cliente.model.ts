import { Moment } from 'moment';

export interface ICliente {
  id?: number;
  nome?: string;
  sobrenome?: string;
  email?: string;
  contato?: string;
  status?: string;
  data?: Moment;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nome?: string,
    public sobrenome?: string,
    public email?: string,
    public contato?: string,
    public status?: string,
    public data?: Moment
  ) {}
}
