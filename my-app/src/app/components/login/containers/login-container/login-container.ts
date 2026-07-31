import { Component, inject } from '@angular/core';
import { FormControl, NonNullableFormBuilder, Validators } from '@angular/forms';
import { LoginView } from '../../views/login-view/login-view';
import { AuthService } from '../../../../core/services/auth-service';
import { Router } from '@angular/router';

type LoginForm = {
  username: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login-container',
  imports: [LoginView],
  templateUrl: './login-container.html',
})
export class LoginContainer {
  private readonly _formBuilder = inject(NonNullableFormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    username: this._formBuilder.control('', [Validators.required, Validators.minLength(3)]),
    password: this._formBuilder.control('', [Validators.required, Validators.minLength(6)]),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      const data = this.loginFormGroup.getRawValue();

      const request = {
        username: data.username,
        password: data.password,
      };

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
