import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/empresa.model';
import { EmpresaService } from './empresa.service';

@Component({
  selector: 'jhi-empresa-delete-dialog',
  templateUrl: './empresa-delete-dialog.component.html'
})
export class EmpresaDeleteDialogComponent {
  empresa: IEmpresa;

  constructor(protected empresaService: EmpresaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.empresaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'empresaListModification',
        content: 'Deleted an empresa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-empresa-delete-popup',
  template: ''
})
export class EmpresaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(EmpresaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.empresa = empresa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/empresa', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/empresa', { outlets: { popup: null } }]);
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
