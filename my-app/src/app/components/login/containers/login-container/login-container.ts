import { Component, inject } from '@angular/core';
import { FormControl, NonNullableFormBuilder, Validators } from '@angular/forms';
import { LoginView } from '../../views/login-view/login-view';

type LoginForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login-container',
  imports: [LoginView],
  templateUrl: './login-container.html',
})
export class LoginContainer {
  private readonly _formBuilder = inject(NonNullableFormBuilder);

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', [Validators.required, Validators.email]),
    password: this._formBuilder.control('', [Validators.required, Validators.minLength(6)]),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      console.log('getRawValue(): ', this.loginFormGroup.getRawValue());
      console.log('value: ', this.loginFormGroup.value);
    }
  }
}
