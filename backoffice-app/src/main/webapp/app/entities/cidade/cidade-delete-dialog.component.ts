import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICidade } from 'app/shared/model/cidade.model';
import { CidadeService } from './cidade.service';

@Component({
  selector: 'jhi-cidade-delete-dialog',
  templateUrl: './cidade-delete-dialog.component.html'
})
export class CidadeDeleteDialogComponent {
  cidade: ICidade;

  constructor(protected cidadeService: CidadeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cidadeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cidadeListModification',
        content: 'Deleted an cidade'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cidade-delete-popup',
  template: ''
})
export class CidadeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cidade }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CidadeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.cidade = cidade;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/cidade', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/cidade', { outlets: { popup: null } }]);
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
