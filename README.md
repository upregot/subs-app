# 🔄 Subscription Manager

> Una aplicación Full Stack para gestionar suscripciones recurrentes y recibir alertas de pago por correo electrónico antes de que te cobren.

<img width="1600" height="761" alt="image" src="https://github.com/user-attachments/assets/4bd2689e-40a4-48a1-9c3d-5678e9a258f0" />


## 🚀 Características Principales

*   **🔐 Seguridad Robusta:** Autenticación JWT completa (Login, Registro, Protección de Rutas).
*   **📩 Notificaciones Inteligentes:** Un **Cron Job** verifica diariamente vencimientos y envía alertas por email 3 días antes del cobro.
*   **🎨 UI Moderna:** Interfaz estilo "Apple/iOS" construida con **Angular 17+** (Standalone Components) y Bootstrap.
*   **🐳 Dockerizado:** Despliegue de un solo comando para Backend, Frontend y Base de Datos.
*   **💸 Lógica Financiera:** Cálculo automático de fechas de próximo cobro basado en la frecuencia (Mensual, Anual, Semanal).

## 🛠️ Tech Stack

**Backend:**
*   Java 21
*   Spring Boot 3
*   Spring Security (JWT)
*   Spring Data JPA
*   JavaMailSender (Integración SMTP)

**Frontend:**
*   Angular 17+ (Standalone)
*   TypeScript
*   Bootstrap 5 & SCSS
*   RxJS (Interceptors & State)

**Infraestructura:**
*   PostgreSQL
*   Docker & Docker Compose
*   Nginx (Reverse Proxy)

## ⚡ Cómo ejecutarlo (Docker)

¡Es muy fácil! No necesitas instalar Java, Node ni Postgres. Solo necesitas Docker.

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/TU_USUARIO/subscription-manager.git
    cd subscription-manager
    ```

2.  **Arrancar la aplicación:**
    ```bash
    docker-compose up --build
    ```

3.  **Acceder:**
    Ve a `http://localhost` en tu navegador.

## 🏗️ Arquitectura

El proyecto utiliza despliegue dockerizado con separación de servicios:

*   **Frontend (Puerto 80):** Servido por Nginx. Consume la API REST.
*   **Backend (Puerto 8080):** Expone endpoints REST y maneja la lógica de negocio.
*   **Database (Puerto 5432):** Persistencia de datos con PostgreSQL.

## 📸 Galería
* login
  <img width="1600" height="757" alt="image" src="https://github.com/user-attachments/assets/ab138d52-4b8f-40a3-b2bc-b97010a2a0b6" />
* form
  <img width="1600" height="757" alt="image" src="https://github.com/user-attachments/assets/6331d649-f5c6-4151-9dca-214878527396" />
* mail
  <img width="1600" height="760" alt="image" src="https://github.com/user-attachments/assets/1546c9cb-c3a1-4757-a2b5-c5aeecb2cd64" />
---
Desarrollado por ulises.
