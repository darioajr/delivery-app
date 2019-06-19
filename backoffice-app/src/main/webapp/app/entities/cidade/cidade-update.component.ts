import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICidade, Cidade } from 'app/shared/model/cidade.model';
import { CidadeService } from './cidade.service';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from 'app/entities/empresa';
import { IMotoboy } from 'app/shared/model/motoboy.model';
import { MotoboyService } from 'app/entities/motoboy';

@Component({
  selector: 'jhi-cidade-update',
  templateUrl: './cidade-update.component.html'
})
export class CidadeUpdateComponent implements OnInit {
  cidade: ICidade;
  isSaving: boolean;

  empresas: IEmpresa[];

  motoboys: IMotoboy[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    estado: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cidadeService: CidadeService,
    protected empresaService: EmpresaService,
    protected motoboyService: MotoboyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cidade }) => {
      this.updateForm(cidade);
      this.cidade = cidade;
    });
    this.empresaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEmpresa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEmpresa[]>) => response.body)
      )
      .subscribe((res: IEmpresa[]) => (this.empresas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.motoboyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMotoboy[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMotoboy[]>) => response.body)
      )
      .subscribe((res: IMotoboy[]) => (this.motoboys = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cidade: ICidade) {
    this.editForm.patchValue({
      id: cidade.id,
      nome: cidade.nome,
      estado: cidade.estado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cidade = this.createFromForm();
    if (cidade.id !== undefined) {
      this.subscribeToSaveResponse(this.cidadeService.update(cidade));
    } else {
      this.subscribeToSaveResponse(this.cidadeService.create(cidade));
    }
  }

  private createFromForm(): ICidade {
    const entity = {
      ...new Cidade(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      estado: this.editForm.get(['estado']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICidade>>) {
    result.subscribe((res: HttpResponse<ICidade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEmpresaById(index: number, item: IEmpresa) {
    return item.id;
  }

  trackMotoboyById(index: number, item: IMotoboy) {
    return item.id;
  }
}
