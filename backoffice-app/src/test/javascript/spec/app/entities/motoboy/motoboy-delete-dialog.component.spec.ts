/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BackofficeTestModule } from '../../../test.module';
import { MotoboyDeleteDialogComponent } from 'app/entities/motoboy/motoboy-delete-dialog.component';
import { MotoboyService } from 'app/entities/motoboy/motoboy.service';

describe('Component Tests', () => {
  describe('Motoboy Management Delete Component', () => {
    let comp: MotoboyDeleteDialogComponent;
    let fixture: ComponentFixture<MotoboyDeleteDialogComponent>;
    let service: MotoboyService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackofficeTestModule],
        declarations: [MotoboyDeleteDialogComponent]
      })
        .overrideTemplate(MotoboyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MotoboyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotoboyService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
