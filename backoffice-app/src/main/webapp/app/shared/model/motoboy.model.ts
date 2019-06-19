import { Moment } from 'moment';

export interface IMotoboy {
  id?: number;
  nome?: string;
  sobrenome?: string;
  cnh?: string;
  placa?: string;
  email?: string;
  contato?: string;
  endereco?: string;
  enderecoNumero?: string;
  enderecoComplemento?: string;
  enderecoBairro?: string;
  cep?: string;
  status?: string;
  data?: Moment;
  cidadeId?: number;
}

export class Motoboy implements IMotoboy {
  constructor(
    public id?: number,
    public nome?: string,
    public sobrenome?: string,
    public cnh?: string,
    public placa?: string,
    public email?: string,
    public contato?: string,
    public endereco?: string,
    public enderecoNumero?: string,
    public enderecoComplemento?: string,
    public enderecoBairro?: string,
    public cep?: string,
    public status?: string,
    public data?: Moment,
    public cidadeId?: number
  ) {}
}
