/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BackofficeTestModule } from '../../../test.module';
import { CidadeUpdateComponent } from 'app/entities/cidade/cidade-update.component';
import { CidadeService } from 'app/entities/cidade/cidade.service';
import { Cidade } from 'app/shared/model/cidade.model';

describe('Component Tests', () => {
  describe('Cidade Management Update Component', () => {
    let comp: CidadeUpdateComponent;
    let fixture: ComponentFixture<CidadeUpdateComponent>;
    let service: CidadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackofficeTestModule],
        declarations: [CidadeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CidadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CidadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CidadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cidade(123);
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
        const entity = new Cidade();
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
