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

type AuthInputOptions = InputOptionsWithoutTransform<boolean> & { alias: 'appAuthVisibility' };
const authInputConfig: AuthInputOptions = { alias: 'appAuthVisibility' };

@Directive({
  selector: '[appAuthDirective]',
})
export class AuthDirective {
  private readonly authService = inject(AuthService);

  readonly showIfAuthenticated = input.required<boolean>(authInputConfig);

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
