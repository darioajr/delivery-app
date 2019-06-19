/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BackofficeTestModule } from '../../../test.module';
import { EmpresaUpdateComponent } from 'app/entities/empresa/empresa-update.component';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { Empresa } from 'app/shared/model/empresa.model';

describe('Component Tests', () => {
  describe('Empresa Management Update Component', () => {
    let comp: EmpresaUpdateComponent;
    let fixture: ComponentFixture<EmpresaUpdateComponent>;
    let service: EmpresaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackofficeTestModule],
        declarations: [EmpresaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmpresaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmpresaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmpresaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Empresa(123);
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
        const entity = new Empresa();
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
