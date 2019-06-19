import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMotoboy, Motoboy } from 'app/shared/model/motoboy.model';
import { MotoboyService } from './motoboy.service';
import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from 'app/entities/cidade';

@Component({
  selector: 'jhi-motoboy-update',
  templateUrl: './motoboy-update.component.html'
})
export class MotoboyUpdateComponent implements OnInit {
  motoboy: IMotoboy;
  isSaving: boolean;

  cidades: ICidade[];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    sobrenome: [null, [Validators.required]],
    cnh: [null, [Validators.required]],
    placa: [null, [Validators.required]],
    email: [null, [Validators.required]],
    contato: [null, [Validators.required]],
    endereco: [null, [Validators.required]],
    enderecoNumero: [null, [Validators.required]],
    enderecoComplemento: [null, [Validators.required]],
    enderecoBairro: [null, [Validators.required]],
    cep: [null, [Validators.required]],
    status: [null, [Validators.required]],
    data: [null, [Validators.required]],
    cidadeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected motoboyService: MotoboyService,
    protected cidadeService: CidadeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ motoboy }) => {
      this.updateForm(motoboy);
      this.motoboy = motoboy;
    });
    this.cidadeService
      .query({ filter: 'motoboy-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICidade[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICidade[]>) => response.body)
      )
      .subscribe(
        (res: ICidade[]) => {
          if (!this.motoboy.cidadeId) {
            this.cidades = res;
          } else {
            this.cidadeService
              .find(this.motoboy.cidadeId)
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

  updateForm(motoboy: IMotoboy) {
    this.editForm.patchValue({
      id: motoboy.id,
      nome: motoboy.nome,
      sobrenome: motoboy.sobrenome,
      cnh: motoboy.cnh,
      placa: motoboy.placa,
      email: motoboy.email,
      contato: motoboy.contato,
      endereco: motoboy.endereco,
      enderecoNumero: motoboy.enderecoNumero,
      enderecoComplemento: motoboy.enderecoComplemento,
      enderecoBairro: motoboy.enderecoBairro,
      cep: motoboy.cep,
      status: motoboy.status,
      data: motoboy.data,
      cidadeId: motoboy.cidadeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const motoboy = this.createFromForm();
    if (motoboy.id !== undefined) {
      this.subscribeToSaveResponse(this.motoboyService.update(motoboy));
    } else {
      this.subscribeToSaveResponse(this.motoboyService.create(motoboy));
    }
  }

  private createFromForm(): IMotoboy {
    const entity = {
      ...new Motoboy(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      sobrenome: this.editForm.get(['sobrenome']).value,
      cnh: this.editForm.get(['cnh']).value,
      placa: this.editForm.get(['placa']).value,
      email: this.editForm.get(['email']).value,
      contato: this.editForm.get(['contato']).value,
      endereco: this.editForm.get(['endereco']).value,
      enderecoNumero: this.editForm.get(['enderecoNumero']).value,
      enderecoComplemento: this.editForm.get(['enderecoComplemento']).value,
      enderecoBairro: this.editForm.get(['enderecoBairro']).value,
      cep: this.editForm.get(['cep']).value,
      status: this.editForm.get(['status']).value,
      data: this.editForm.get(['data']).value,
      cidadeId: this.editForm.get(['cidadeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMotoboy>>) {
    result.subscribe((res: HttpResponse<IMotoboy>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
