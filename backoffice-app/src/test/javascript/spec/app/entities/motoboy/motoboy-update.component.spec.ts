/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BackofficeTestModule } from '../../../test.module';
import { MotoboyUpdateComponent } from 'app/entities/motoboy/motoboy-update.component';
import { MotoboyService } from 'app/entities/motoboy/motoboy.service';
import { Motoboy } from 'app/shared/model/motoboy.model';

describe('Component Tests', () => {
  describe('Motoboy Management Update Component', () => {
    let comp: MotoboyUpdateComponent;
    let fixture: ComponentFixture<MotoboyUpdateComponent>;
    let service: MotoboyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackofficeTestModule],
        declarations: [MotoboyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MotoboyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MotoboyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MotoboyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Motoboy(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Motoboy();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
