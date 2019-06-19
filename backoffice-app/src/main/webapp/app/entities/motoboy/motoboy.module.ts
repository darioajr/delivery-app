import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BackofficeSharedModule } from 'app/shared';
import {
  MotoboyComponent,
  MotoboyDetailComponent,
  MotoboyUpdateComponent,
  MotoboyDeletePopupComponent,
  MotoboyDeleteDialogComponent,
  motoboyRoute,
  motoboyPopupRoute
} from './';

const ENTITY_STATES = [...motoboyRoute, ...motoboyPopupRoute];

@NgModule({
  imports: [BackofficeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MotoboyComponent,
    MotoboyDetailComponent,
    MotoboyUpdateComponent,
    MotoboyDeleteDialogComponent,
    MotoboyDeletePopupComponent
  ],
  entryComponents: [MotoboyComponent, MotoboyUpdateComponent, MotoboyDeleteDialogComponent, MotoboyDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BackofficeMotoboyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
