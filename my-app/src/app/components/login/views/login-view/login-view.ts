import { Component, input, output } from '@angular/core';
import { FormGroup, NgForm, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { LoginForm } from '../../containers/login-container/login-container';

@Component({
  selector: 'app-login-view',
  imports: [ReactiveFormsModule, MatButton],
  templateUrl: './login-view.html',
})
export class LoginView {
  loginFormGroup = input.required<LoginForm>();
  submitEvent = output<void>();

  onFormSubmit() {
    this.submitEvent.emit();
  }
}
