/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackofficeTestModule } from '../../../test.module';
import { CidadeDetailComponent } from 'app/entities/cidade/cidade-detail.component';
import { Cidade } from 'app/shared/model/cidade.model';

describe('Component Tests', () => {
  describe('Cidade Management Detail Component', () => {
    let comp: CidadeDetailComponent;
    let fixture: ComponentFixture<CidadeDetailComponent>;
    const route = ({ data: of({ cidade: new Cidade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackofficeTestModule],
        declarations: [CidadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CidadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CidadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cidade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
