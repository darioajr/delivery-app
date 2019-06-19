import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMotoboy } from 'app/shared/model/motoboy.model';
import { MotoboyService } from './motoboy.service';

@Component({
  selector: 'jhi-motoboy-delete-dialog',
  templateUrl: './motoboy-delete-dialog.component.html'
})
export class MotoboyDeleteDialogComponent {
  motoboy: IMotoboy;

  constructor(protected motoboyService: MotoboyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.motoboyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'motoboyListModification',
        content: 'Deleted an motoboy'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-motoboy-delete-popup',
  template: ''
})
export class MotoboyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ motoboy }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MotoboyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.motoboy = motoboy;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/motoboy', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/motoboy', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
