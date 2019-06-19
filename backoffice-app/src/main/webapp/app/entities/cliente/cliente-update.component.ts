import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  cliente: ICliente;
  isSaving: boolean;
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    sobrenome: [null, [Validators.required]],
    email: [null, [Validators.required]],
    contato: [null, [Validators.required]],
    status: [null, [Validators.required]],
    data: [null, [Validators.required]]
  });

  constructor(protected clienteService: ClienteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
      this.cliente = cliente;
    });
  }

  updateForm(cliente: ICliente) {
    this.editForm.patchValue({
      id: cliente.id,
      nome: cliente.nome,
      sobrenome: cliente.sobrenome,
      email: cliente.email,
      contato: cliente.contato,
      status: cliente.status,
      data: cliente.data
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    const entity = {
      ...new Cliente(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      sobrenome: this.editForm.get(['sobrenome']).value,
      email: this.editForm.get(['email']).value,
      contato: this.editForm.get(['contato']).value,
      status: this.editForm.get(['status']).value,
      data: this.editForm.get(['data']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
    result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
