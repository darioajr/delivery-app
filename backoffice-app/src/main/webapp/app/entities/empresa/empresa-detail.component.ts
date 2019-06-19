import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmpresa } from 'app/shared/model/empresa.model';

@Component({
  selector: 'jhi-empresa-detail',
  templateUrl: './empresa-detail.component.html'
})
export class EmpresaDetailComponent implements OnInit {
  empresa: IEmpresa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.empresa = empresa;
    });
  }

  previousState() {
    window.history.back();
  }
}
