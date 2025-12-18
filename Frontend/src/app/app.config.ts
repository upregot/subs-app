import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http'; // <--- IMPORTANTE

import { routes } from './app.routes';
import { authInterceptor } from './core/interceptors/auth-interceptor'; // <--- IMPORTA TU INTERCEPTOR

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    // AQUÍ ESTÁ LA CLAVE:
    provideHttpClient(
      withFetch(),
      withInterceptors([authInterceptor]) // <--- ¡TIENE QUE ESTAR AQUÍ!
    )
  ]
};