/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MotoboyService } from 'app/entities/motoboy/motoboy.service';
import { IMotoboy, Motoboy } from 'app/shared/model/motoboy.model';

describe('Service Tests', () => {
  describe('Motoboy Service', () => {
    let injector: TestBed;
    let service: MotoboyService;
    let httpMock: HttpTestingController;
    let elemDefault: IMotoboy;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(MotoboyService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Motoboy(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            data: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Motoboy', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            data: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .create(new Motoboy(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Motoboy', async () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            sobrenome: 'BBBBBB',
            cnh: 'BBBBBB',
            placa: 'BBBBBB',
            email: 'BBBBBB',
            contato: 'BBBBBB',
            endereco: 'BBBBBB',
            enderecoNumero: 'BBBBBB',
            enderecoComplemento: 'BBBBBB',
            enderecoBairro: 'BBBBBB',
            cep: 'BBBBBB',
            status: 'BBBBBB',
            data: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Motoboy', async () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            sobrenome: 'BBBBBB',
            cnh: 'BBBBBB',
            placa: 'BBBBBB',
            email: 'BBBBBB',
            contato: 'BBBBBB',
            endereco: 'BBBBBB',
            enderecoNumero: 'BBBBBB',
            enderecoComplemento: 'BBBBBB',
            enderecoBairro: 'BBBBBB',
            cep: 'BBBBBB',
            status: 'BBBBBB',
            data: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            data: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Motoboy', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
