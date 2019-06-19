import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BackofficeSharedModule } from 'app/shared';
import {
  CidadeComponent,
  CidadeDetailComponent,
  CidadeUpdateComponent,
  CidadeDeletePopupComponent,
  CidadeDeleteDialogComponent,
  cidadeRoute,
  cidadePopupRoute
} from './';

const ENTITY_STATES = [...cidadeRoute, ...cidadePopupRoute];

@NgModule({
  imports: [BackofficeSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CidadeComponent, CidadeDetailComponent, CidadeUpdateComponent, CidadeDeleteDialogComponent, CidadeDeletePopupComponent],
  entryComponents: [CidadeComponent, CidadeUpdateComponent, CidadeDeleteDialogComponent, CidadeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BackofficeCidadeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
