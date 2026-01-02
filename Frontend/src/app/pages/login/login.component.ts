import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; 
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth/auth' 

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink], // <--- Importamos FormsModule aquí
  templateUrl: './login.html' ,
  styleUrl: './login.scss'
})
export class LoginComponent {

  // Inyección de dependencias moderna
  private authService = inject(AuthService);
  private router = inject(Router);

  // Datos del formulario
  loginData = {
    username: '',
    password: ''
  };

  onLogin() {
    // Aquí llamaremos al servicio
    console.log('Intentando loguear con:', this.loginData);
    
    this.authService.login(this.loginData).subscribe({
      next: (response: any) => {
        console.log('Login exitoso:', response);
        // Guardamos el token (esto lo mejoraremos luego)
        localStorage.setItem('token', response.token);
        // Redirigimos al dashboard
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.error('Error de login:', err);
        alert('Usuario o contraseña incorrectos');
      }
    });
  }
}