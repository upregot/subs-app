import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core'; // <--- 1. IMPORTAR AQUÍ

import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router'; 
// CORRECCIÓN 1: Ruta del archivo (asumiendo estándar Angular)
import { SubscriptionService, Subscription } from '../../services/subscription/subscription';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink], 
  // CORRECCIÓN 2: Nombres de archivo estándar
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss'
})
export class DashboardComponent implements OnInit {

    subscriptions: Subscription[] = [];
    // Inyección
    private subscriptionService = inject(SubscriptionService);
        private cd = inject(ChangeDetectorRef); // <--- 2. INYECTAR AQUÍ


    ngOnInit(): void {
      this.loadSubscriptions();
    }

    loadSubscriptions() {
      // CORRECCIÓN 3: Usar el nombre correcto del método (getMySubscriptions)
      this.subscriptionService.getMySubscriptions().subscribe({
        next: (data) => {
          this.subscriptions = data;
          console.log('Datos recibidos:', data);
          this.cd.detectChanges(); // <--- 3. ¡DESPIERTA ANGULAR! (Fuerza la actualización)


        },
        error: (err) => {
          console.error('Error loading subscriptions', err);
        }
      });
    }

    deleteSubscription(id: number) {
      // Validamos que id exista
      if (!id) return; 

      if (confirm('¿Estás seguro de eliminar esta suscripción?')) {
        this.subscriptionService.deleteSubscription(id).subscribe(() => {
          this.loadSubscriptions(); // Recargar lista
        });
      }
    }
}