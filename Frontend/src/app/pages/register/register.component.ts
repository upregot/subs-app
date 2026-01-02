import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth'; 

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink], 
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class RegisterComponent {

  private authService = inject(AuthService);
  private router = inject(Router);

  registerData = {
    username: '',
    email: '',
    password: ''
  };

  onSubmit() {
    // Validar espacios en blanco
    this.registerData.username = this.registerData.username.trim();
    this.registerData.email = this.registerData.email.trim();

    this.authService.register(this.registerData).subscribe({
      next: () => {
        // Éxito: Redirigir al login
        alert('¡Cuenta creada con éxito! Por favor inicia sesión.');
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Error registro:', err);
        alert('Error: El usuario o email ya existen, o los datos son inválidos.');
      }
    });
  }
}