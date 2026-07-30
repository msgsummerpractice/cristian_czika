import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'usernameFormat',
})
export class UsernameFormatPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) {
      return '';
    }

    const trimmedUsername = value.trim();

    if (!trimmedUsername.startsWith('@')) {
      return `@${trimmedUsername.toLowerCase()}`;
    }

    return trimmedUsername.toLowerCase();
  }
}
