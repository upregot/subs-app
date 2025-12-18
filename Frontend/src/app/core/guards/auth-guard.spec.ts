import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  // Aquí pondremos la lógica real más tarde (verificar token)
  
  // Por ahora devolvemos TRUE para que no te bloquee el desarrollo
  return true;
};