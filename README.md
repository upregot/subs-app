# 🔄 Subscription Manager

> Una aplicación Full Stack para gestionar suscripciones recurrentes y recibir alertas de pago por correo electrónico antes de que te cobren.

![Dashboard Preview](<img width="1600" height="761" alt="image" src="https://github.com/user-attachments/assets/f7b78993-d607-4c7a-a84a-4b4f50314d96" />
)
*(Reemplaza esto con tu captura del dashboard)*

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

El proyecto utiliza una arquitectura de microservicios simplificada en contenedores:

*   **Frontend (Puerto 80):** Servido por Nginx. Consume la API REST.
*   **Backend (Puerto 8080):** Expone endpoints REST y maneja la lógica de negocio.
*   **Database (Puerto 5432):** Persistencia de datos con PostgreSQL.

## 📸 Galería

| Login | Crear Suscripción | Alerta Email |
|-------|-------------------|--------------|
| ![Login](<img width="1600" height="757" alt="image" src="https://github.com/user-attachments/assets/7533423b-4fba-467e-89ac-7a369e9d3bcd" />
) | ![Form](<img width="1600" height="757" alt="image" src="https://github.com/user-attachments/assets/41eaf84b-2ed4-4070-bf61-3ce5f297d172" />
) | ![Email](<img width="1600" height="760" alt="image" src="https://github.com/user-attachments/assets/578232bc-ef57-4506-8d28-9150615d94fd" />
) |

---
Desarrollado con ❤️ por upregot!
