import { Component, input, output } from '@angular/core';
import { FormGroup, NgForm, ReactiveFormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-login-view',
  imports: [ReactiveFormsModule, MatButton],
  templateUrl: './login-view.html',
})
export class LoginView {
  loginFormGroup = input.required<FormGroup>();
  submitEvent = output<void>();

  onFormSubmit() {
    this.submitEvent.emit();
  }
}
