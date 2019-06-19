import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BackofficeSharedLibsModule, BackofficeSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BackofficeSharedLibsModule, BackofficeSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BackofficeSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BackofficeSharedModule {
  static forRoot() {
    return {
      ngModule: BackofficeSharedModule
    };
  }
}
