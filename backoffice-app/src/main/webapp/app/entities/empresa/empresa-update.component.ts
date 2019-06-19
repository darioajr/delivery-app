import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEmpresa, Empresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';
import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from 'app/entities/cidade';

@Component({
  selector: 'jhi-empresa-update',
  templateUrl: './empresa-update.component.html'
})
export class EmpresaUpdateComponent implements OnInit {
  empresa: IEmpresa;
  isSaving: boolean;

  cidades: ICidade[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    razaoSocial: [null, [Validators.required]],
    nomeFanstasia: [],
    cnpj: [null, [Validators.required]],
    email: [null, [Validators.required]],
    contato: [null, [Validators.required]],
    status: [null, [Validators.required]],
    recargaPendente: [null, [Validators.required]],
    possuiBau: [null, [Validators.required]],
    endereco: [null, [Validators.required]],
    enderecoNumero: [null, [Validators.required]],
    enderecoComplemento: [null, [Validators.required]],
    enderecoBairro: [null, [Validators.required]],
    cep: [null, [Validators.required]],
    cidadeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected empresaService: EmpresaService,
    protected cidadeService: CidadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.updateForm(empresa);
      this.empresa = empresa;
    });
    this.cidadeService
      .query({ filter: 'empresa-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICidade[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICidade[]>) => response.body)
      )
      .subscribe(
        (res: ICidade[]) => {
          if (!this.empresa.cidadeId) {
            this.cidades = res;
          } else {
            this.cidadeService
              .find(this.empresa.cidadeId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICidade>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICidade>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICidade) => (this.cidades = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(empresa: IEmpresa) {
    this.editForm.patchValue({
      id: empresa.id,
      nome: empresa.nome,
      razaoSocial: empresa.razaoSocial,
      nomeFanstasia: empresa.nomeFanstasia,
      cnpj: empresa.cnpj,
      email: empresa.email,
      contato: empresa.contato,
      status: empresa.status,
      recargaPendente: empresa.recargaPendente,
      possuiBau: empresa.possuiBau,
      endereco: empresa.endereco,
      enderecoNumero: empresa.enderecoNumero,
      enderecoComplemento: empresa.enderecoComplemento,
      enderecoBairro: empresa.enderecoBairro,
      cep: empresa.cep,
      cidadeId: empresa.cidadeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const empresa = this.createFromForm();
    if (empresa.id !== undefined) {
      this.subscribeToSaveResponse(this.empresaService.update(empresa));
    } else {
      this.subscribeToSaveResponse(this.empresaService.create(empresa));
    }
  }

  private createFromForm(): IEmpresa {
    const entity = {
      ...new Empresa(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      razaoSocial: this.editForm.get(['razaoSocial']).value,
      nomeFanstasia: this.editForm.get(['nomeFanstasia']).value,
      cnpj: this.editForm.get(['cnpj']).value,
      email: this.editForm.get(['email']).value,
      contato: this.editForm.get(['contato']).value,
      status: this.editForm.get(['status']).value,
      recargaPendente: this.editForm.get(['recargaPendente']).value,
      possuiBau: this.editForm.get(['possuiBau']).value,
      endereco: this.editForm.get(['endereco']).value,
      enderecoNumero: this.editForm.get(['enderecoNumero']).value,
      enderecoComplemento: this.editForm.get(['enderecoComplemento']).value,
      enderecoBairro: this.editForm.get(['enderecoBairro']).value,
      cep: this.editForm.get(['cep']).value,
      cidadeId: this.editForm.get(['cidadeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresa>>) {
    result.subscribe((res: HttpResponse<IEmpresa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCidadeById(index: number, item: ICidade) {
    return item.id;
  }
}
