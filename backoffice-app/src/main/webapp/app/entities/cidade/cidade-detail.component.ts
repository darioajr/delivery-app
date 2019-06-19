import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICidade } from 'app/shared/model/cidade.model';

@Component({
  selector: 'jhi-cidade-detail',
  templateUrl: './cidade-detail.component.html'
})
export class CidadeDetailComponent implements OnInit {
  cidade: ICidade;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cidade }) => {
      this.cidade = cidade;
    });
  }

  previousState() {
    window.history.back();
  }
}
