import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // <--- 1. IMPORTANTE
import { Router, RouterLink } from '@angular/router';
import { SubscriptionService, Subscription } from '../../services/subscription/subscription';

@Component({
  selector: 'app-subscription-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink], // <--- 2. AGREGAR AQUÍ
  templateUrl: './subscription-form.html',
  styleUrl: './subscription-form.scss'
})
export class SubscriptionFormComponent {

  private subService = inject(SubscriptionService);
  private router = inject(Router);

  // Modelo de datos inicial
  subData: Subscription = {
    name: '',
    price: 0,
    currency: 'USD',
    frequency: 'MONTHLY',
    startDate: new Date().toISOString().split('T')[0], // Fecha de hoy formato YYYY-MM-DD
    nextBillingDate: '' // El backend la calcula, pero el tipo la pide (la mandamos vacía o ignorada)
  };
name: any;

  onSubmit() {
    console.log('Enviando:', this.subData);

    // OJO: El backend calcula nextBillingDate, no necesitamos enviarla o la mandamos igual que startDate
    // Para simplificar, asignamos startDate a nextBillingDate temporalmente si el backend valida @NotNull
    // Pero tu backend corregido ya debería calcularla solo.
    this.subData.nextBillingDate = this.subData.startDate; 

    this.subService.createSubscription(this.subData).subscribe({
      next: (res) => {
        console.log('Creado!', res);
        this.router.navigate(['/dashboard']); // Volver al dashboard
      },
      error: (err) => {
        console.error('Error al crear:', err);
        alert('Error al guardar. Revisa la consola.');
      }
    });
  }
}