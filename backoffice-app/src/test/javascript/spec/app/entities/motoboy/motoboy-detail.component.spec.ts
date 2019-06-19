/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackofficeTestModule } from '../../../test.module';
import { MotoboyDetailComponent } from 'app/entities/motoboy/motoboy-detail.component';
import { Motoboy } from 'app/shared/model/motoboy.model';

describe('Component Tests', () => {
  describe('Motoboy Management Detail Component', () => {
    let comp: MotoboyDetailComponent;
    let fixture: ComponentFixture<MotoboyDetailComponent>;
    const route = ({ data: of({ motoboy: new Motoboy(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackofficeTestModule],
        declarations: [MotoboyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MotoboyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MotoboyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.motoboy).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
