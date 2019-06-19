import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMotoboy } from 'app/shared/model/motoboy.model';

@Component({
  selector: 'jhi-motoboy-detail',
  templateUrl: './motoboy-detail.component.html'
})
export class MotoboyDetailComponent implements OnInit {
  motoboy: IMotoboy;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ motoboy }) => {
      this.motoboy = motoboy;
    });
  }

  previousState() {
    window.history.back();
  }
}
