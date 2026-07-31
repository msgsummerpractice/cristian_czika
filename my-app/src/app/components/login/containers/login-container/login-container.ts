import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, Validators } from '@angular/forms';
import { LoginView } from '../../views/login-view/login-view';
import { AuthService } from '../../../../core/services/auth-service';
import { Router } from '@angular/router';
import { SignInRequest } from '../../../../core/model/user.model';

export type LoginForm = FormGroup<{
  username: FormControl<string>;
  password: FormControl<string>;
}>;

@Component({
  selector: 'app-login-container',
  imports: [LoginView],
  template: '<app-login-view [loginFormGroup]="loginFormGroup" (submitEvent)="onFormSubmit()"/>',
})
export class LoginContainer {
  private readonly _formBuilder = inject(NonNullableFormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly loginFormGroup: LoginForm = this._formBuilder.group({
    username: this._formBuilder.control('', [Validators.required, Validators.minLength(3)]),
    password: this._formBuilder.control('', [Validators.required, Validators.minLength(6)]),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      const request: SignInRequest = this.loginFormGroup.getRawValue();

      this.authService.signIn(request).subscribe({
        next: () => {
          this.router.navigate(['/home']);
        },
        error: (error) => {
          console.error(error);
        },
      });
    }
  }
}
