import { CanDeactivateFn } from '@angular/router';

export const confirmExitGuard: CanDeactivateFn<unknown> = (
  component,
  currentRoute,
  currentState,
  nextState,
) => {
  return confirm('Are you sure you want to leave this page?');
};
