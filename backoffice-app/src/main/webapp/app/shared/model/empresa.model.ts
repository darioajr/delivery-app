export interface IEmpresa {
  id?: number;
  nome?: string;
  razaoSocial?: string;
  nomeFanstasia?: string;
  cnpj?: string;
  email?: string;
  contato?: string;
  status?: string;
  recargaPendente?: string;
  possuiBau?: string;
  endereco?: string;
  enderecoNumero?: string;
  enderecoComplemento?: string;
  enderecoBairro?: string;
  cep?: string;
  cidadeId?: number;
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public nome?: string,
    public razaoSocial?: string,
    public nomeFanstasia?: string,
    public cnpj?: string,
    public email?: string,
    public contato?: string,
    public status?: string,
    public recargaPendente?: string,
    public possuiBau?: string,
    public endereco?: string,
    public enderecoNumero?: string,
    public enderecoComplemento?: string,
    public enderecoBairro?: string,
    public cep?: string,
    public cidadeId?: number
  ) {}
}
