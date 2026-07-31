import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'usernameFormat',
})
export class UsernameFormatPipe implements PipeTransform {
  transform(value: string | null | undefined): string {
    if (!value) {
      return '';
    }

    const username = value?.trim().toLowerCase();

    if (!username.startsWith('@')) {
      return `@${username}`;
    }

    return username;
  }
}
