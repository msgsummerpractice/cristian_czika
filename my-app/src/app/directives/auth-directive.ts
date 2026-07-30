import { Directive, effect, input, TemplateRef, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appAuthDirective]',
})
export class AuthDirective {
  private isAuthenticated = false;

  showIfAuthenticated = input.required<boolean>({ alias: 'appAuthVisibility ' });

  constructor(
    private templatedRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
  ) {
    effect(() => {
      if (this.isAuthenticated === this.showIfAuthenticated()) {
        this.viewContainer.createEmbeddedView(this.templatedRef);
      } else {
        this.viewContainer.clear();
      }
    });
  }
}
