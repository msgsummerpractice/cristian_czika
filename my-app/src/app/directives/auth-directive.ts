import {
  Directive,
  effect,
  inject,
  input,
  InputOptions,
  InputOptionsWithoutTransform,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';
import { AuthService } from '../core/services/auth-service';

@Directive({
  selector: '[appAuthDirective]',
  standalone: true,
})
export class AuthDirective {
  private readonly authService = inject(AuthService);

  readonly showIfAuthenticated = input.required<boolean>({ alias: 'appAuthVisibility' });

  constructor(
    private templatedRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
  ) {
    effect(() => {
      if (this.authService.isAuthenticated() === this.showIfAuthenticated()) {
        this.viewContainer.createEmbeddedView(this.templatedRef);
      } else {
        this.viewContainer.clear();
      }
    });
  }
}
