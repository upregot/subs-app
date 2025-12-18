import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Definimos qué forma tienen los datos (esto es TypeScript puro)
export interface Subscription {
  id?: number;
  name: string;
  price: number;
  currency: string;
  frequency: string;
  startDate: string;
  nextBillingDate?: string; // Opcional porque el backend lo calcula
  logoUrl?: string;
}

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  // Inyectamos el cliente HTTP moderno
  private http = inject(HttpClient);
  
  // La URL de tu Backend Spring Boot
  private apiUrl = 'http://localhost:8080/api/subscriptions';

  constructor() { }

  // 1. Obtener lista
  getMySubscriptions(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(this.apiUrl);
  }

  // 2. Crear nueva
  createSubscription(sub: Subscription): Observable<Subscription> {
    return this.http.post<Subscription>(this.apiUrl, sub);
  }
  
  // 3. Eliminar (útil para más tarde)
  deleteSubscription(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}